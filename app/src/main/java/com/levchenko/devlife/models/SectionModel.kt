package com.levchenko.devlife.models

class SectionModel(val name: String) {
    var currentGif = 0
    var currentPage = 0
    var gifList: List<GifItemModel> = emptyList()

    fun setData(currentGif: Int, currentPage: Int, gifList: List<GifItemModel>) {
        this.currentGif = currentGif
        this.currentPage = currentPage
        this.gifList = gifList
    }
}