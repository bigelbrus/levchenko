package com.levchenko.devlife

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.*
import dagger.multibindings.IntoMap
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component(modules = [GifScreenModule::class])
interface GifScreenComponent {
    fun inject(activity: GifScreenActivity)
}

@Module
abstract class GifScreenModule {

    @Binds
    abstract fun bindsRepo(repo: GifScreenRepo): IGifScreenRepo

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideApi(retrofit: Retrofit): DevLifeApi = retrofit.create(DevLifeApi::class.java)

        @JvmStatic
        @Provides
        fun provideRetrofit(): Retrofit =
            Retrofit.Builder()
                .baseUrl("http://developerslife.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

}
