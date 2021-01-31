package com.levchenko.devlife

import com.levchenko.devlife.models.GifItemModel
import com.levchenko.devlife.models.ServerResponse
import retrofit2.await
import javax.inject.Inject

interface IGifScreenRepo {
    suspend fun getListOfGifs(
        section: String,
        page: String? = null
    ): ServerResponse<List<GifItemModel>>
}

class GifScreenRepo
@Inject constructor(private val api: DevLifeApi) : IGifScreenRepo {
    override suspend fun getListOfGifs(
        section: String,
        page: String?
    ): ServerResponse<List<GifItemModel>> {
        val response = page?.let {
            api.getGif(section, page)
        } ?: api.getGif(section)
        return response.await()
    }
}