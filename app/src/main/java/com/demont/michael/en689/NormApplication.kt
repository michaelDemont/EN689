package com.demont.michael.en689

import android.app.Application
import timber.log.Timber

class NormApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}