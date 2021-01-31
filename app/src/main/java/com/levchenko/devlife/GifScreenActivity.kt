package com.levchenko.devlife

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import kotlinx.android.synthetic.main.activity_main.*
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.no_connection_error.*
import javax.inject.Inject

class GifScreenActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: GifScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ComponentProvider.getInstance().getGifScreenComponent().inject(this)
        initViews()
        subscribeToViewModels()
        drawLine(buttonLatest)
    }

    private fun subscribeToViewModels() {
        viewModel.gif.observe(this) {
            it?.let {
                gifContainer.isVisible = true
                tvGifDesc.isVisible = true
                viewModel.prefButtonVisibility
                buttonNext.isClickable = true
                errorConnection.isVisible = false

                Glide.with(this)
                    .load(it.gifUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }
                    })
                    .into(gifContainer)

            } ?: run {
                gifContainer.isVisible = false
                tvGifDesc.isVisible = false
                buttonNext.isClickable = false
            }
        }
        viewModel.error.observe(this) {
            errorText.text = it
            errorConnection.isVisible = true
            gifContainer.isVisible = false
        }
        viewModel.prefButtonVisibility.observe(this) {
            buttonBack.isInvisible = !it
        }
        viewModel.text.observe(this) {
            tvGifDesc.text = it
        }
    }

    private fun initViews() {
        buttonNext.setOnClickListener {
            progressBar.isVisible = true
            viewModel.onNextButtonClick()
        }
        buttonBack.setOnClickListener {
            viewModel.onPrevButtonClick()
        }
        buttonHot.setOnClickListener {
            viewModel.onHotClick()
            drawLine(it)
        }
        buttonLatest.setOnClickListener {
            viewModel.onLatestClick()
            drawLine(it)
        }
        buttonTop.setOnClickListener {
            viewModel.onTopClick()
            drawLine(it)
        }
        buttonRetry.setOnClickListener {
            viewModel.onRetryClick()
        }
    }

    private fun drawLine(view: View) {
        buttonLatest.background = null
        buttonHot.background = null
        buttonTop.background = null
        view.background = ContextCompat.getDrawable(this, R.drawable.bg_line)
    }
}