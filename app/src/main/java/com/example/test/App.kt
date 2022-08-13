package com.example.test

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)


        val myModules = module {

            single {  PrinterViewModel() }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

    }
}