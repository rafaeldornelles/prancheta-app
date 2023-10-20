package com.rafael.prancheta

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.rafael.core.cache.TokenCache
import com.rafael.core.cache.UserCache
import com.rafael.core.datasource.di.dataSourceModule
import com.rafael.featureauth.di.authModule
import com.rafael.featurebriefing.di.briefingModule
import com.rafael.featureproject.di.projectModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PranchetaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PranchetaApplication)
            modules(
                applicationModule,
                authModule,
                briefingModule,
                projectModule,
                dataSourceModule
            )
        }
    }
}

val applicationModule = module {
    single<SharedPreferences> { androidContext().getSharedPreferences("prancheta", MODE_PRIVATE) }
    single { UserCache(get()) }
    single { TokenCache(get()) }
}