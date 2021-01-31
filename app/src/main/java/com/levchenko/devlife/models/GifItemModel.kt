package com.levchenko.devlife.models

import com.google.gson.annotations.SerializedName

class GifItemModel (
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("gifURL")
    val gifUrl: String? = null
)
