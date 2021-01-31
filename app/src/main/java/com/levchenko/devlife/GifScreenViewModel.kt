package com.levchenko.devlife

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levchenko.devlife.models.GifItemModel
import com.levchenko.devlife.models.SectionModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class GifScreenViewModel
@Inject constructor(
    private val repo: IGifScreenRepo
) : ViewModel() {
    var currentPage = 0
    var currentGif = -1
    var gifList: List<GifItemModel> = emptyList()

    private val sectionTop = SectionModel("top")
    private val sectionHot = SectionModel("hot")
    private val sectionLatest = SectionModel("latest")
    private var currentSection: SectionModel = sectionLatest

    val gif = MutableLiveData<GifItemModel?>()
    val text = MutableLiveData<String?>()
    val error = MutableLiveData<String>()
    val prefButtonVisibility = MutableLiveData<Boolean>()

    init {
        onNextButtonClick()
        setPrevButtonVisibility()
    }

    private fun getGifList(section: String, page: String, isRepeat: Boolean) {
        viewModelScope.launch {
            runCatching {
                repo.getListOfGifs(section, page)
            }.onSuccess {
                it.result?.let { result ->
                    gifList = gifList + result
                    error.value = "Ничего не пришло"
                }
                if (!isRepeat) {
                    currentPage++
                }
                setupGif()
            }.onFailure {
                error.value = "Ошибка сети"
            }
        }
    }

    fun onNextButtonClick() {
        currentGif++
        downloadOrNot()
        setPrevButtonVisibility()
    }

    private fun downloadOrNot() {
        when {
            gifList.size <= currentGif -> {
                getGifList(currentSection.name, currentPage.toString(), false)
            }
            else -> setupGif()
        }
    }

    fun onPrevButtonClick() {
        --currentGif
        setupGif()
        setPrevButtonVisibility()
    }

    private fun setPrevButtonVisibility() {
        prefButtonVisibility.value = currentGif > 0
    }

    private fun setupGif() {
        if (gifList.isNotEmpty()) {
            gif.value = gifList[currentGif]
            text.value = gifList[currentGif].description
        } else {
            gif.value = null
            text.value = null
        }
    }

    private fun onRepeat() {
        getGifList(currentSection.name, currentPage.toString(), true)
    }

    private fun setNewSection(newSection: SectionModel) {
        currentSection.setData(currentGif, currentPage, gifList)
        currentSection = newSection
        currentGif = currentSection.currentGif
        currentPage = currentSection.currentPage
        gifList = currentSection.gifList
        downloadOrNot()
        setPrevButtonVisibility()
    }

    fun onLatestClick() {
        setNewSection(sectionLatest)
    }

    fun onTopClick() {
        setNewSection(sectionTop)
    }

    fun onHotClick() {
        setNewSection(sectionHot)
    }

    fun onRetryClick() {
        onRepeat()
    }
}