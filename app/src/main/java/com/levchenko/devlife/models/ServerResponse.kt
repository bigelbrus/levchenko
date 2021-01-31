package com.levchenko.devlife.models

import com.google.gson.annotations.SerializedName

class ServerResponse<E> (
    @SerializedName("result")
    val result: E? = null
)