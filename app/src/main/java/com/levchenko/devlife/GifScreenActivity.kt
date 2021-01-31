package com.levchenko.devlife

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        }
        buttonLatest.setOnClickListener {
            viewModel.onLatestClick()
        }
        buttonTop.setOnClickListener {
            viewModel.onTopClick()
        }
        buttonRetry.setOnClickListener {
            viewModel.onRetryClick()
        }
    }
}