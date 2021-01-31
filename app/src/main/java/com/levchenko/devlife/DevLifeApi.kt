package com.levchenko.devlife

import com.levchenko.devlife.models.GifItemModel
import com.levchenko.devlife.models.ServerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DevLifeApi {
    @GET("/{section}/{page}?json=true")
    fun getGif(
        @Path("section") section: String,
        @Path("page") page: String
    ): Call<ServerResponse<List<GifItemModel>>>

    @GET("/{section}?json=true")
    fun getGif(
        @Path("section") section: String
    ): Call<ServerResponse<List<GifItemModel>>>
}