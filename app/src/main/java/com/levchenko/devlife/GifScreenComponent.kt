package com.levchenko.devlife

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.*
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

        val BASE_URL = "http://developerslife.ru"

        @JvmStatic
        @Provides
        fun provideApi(retrofit: Retrofit): DevLifeApi = retrofit.create(DevLifeApi::class.java)

        @JvmStatic
        @Provides
        fun provideRetrofit(): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

}
