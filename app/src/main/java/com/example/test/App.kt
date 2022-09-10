package com.example.test

import android.app.Application
import com.example.test.data.implement.PrinterDataImplement
import com.example.test.data.repository.PrinterDataRepository
import com.example.test.data.source.remote.PrinterDataRemoteDataSource
import com.example.test.service.http.createApiServiceTianaInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)


        val myModules = module {

            single { createApiServiceTianaInstance() }
            factory<PrinterDataRepository> {
                PrinterDataImplement(
                    PrinterDataRemoteDataSource(
                        get()
                    )
                )
            }
            viewModel {  PrinterViewModel(get()) }

            
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

    }
}