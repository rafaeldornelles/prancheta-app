package com.rafael.prancheta

import android.app.Application
import com.rafael.featureauth.di.authModule
import com.rafael.featurebriefing.di.briefingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PranchetaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PranchetaApplication)
            modules(
                authModule,
                briefingModule
            )
        }
    }
}