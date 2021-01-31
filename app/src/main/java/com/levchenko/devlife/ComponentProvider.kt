package com.levchenko.devlife

import android.content.Context

class ComponentProvider private constructor() {

    private var gifScreenComponent: GifScreenComponent? = null

    companion object {

        private var instance: ComponentProvider? = null
        lateinit var context: Context

        fun getInstance(): ComponentProvider {
            instance = instance ?: ComponentProvider()
            return instance!!
        }

        fun setApplicationContext(context: Context) {
            this.context = context
        }
    }

    fun getGifScreenComponent(): GifScreenComponent {
        gifScreenComponent = gifScreenComponent ?: DaggerGifScreenComponent.builder()
            .build()
        return gifScreenComponent as GifScreenComponent
    }
}