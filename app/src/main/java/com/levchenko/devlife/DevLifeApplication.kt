package com.levchenko.devlife

import android.app.Application

class DevLifeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ComponentProvider.setApplicationContext(this)
    }
}