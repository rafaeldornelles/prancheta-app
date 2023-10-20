package com.rafael.core.datasource.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rafael.core.datasource.endpoints.BriefingEndpoint
import com.rafael.core.datasource.endpoints.ProjectEndpoint
import com.rafael.core.datasource.endpoints.UserEndpoint
import com.rafael.core.datasource.interceptors.AuthInterceptor
import com.rafael.core.datasource.interceptors.PranchetaCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://prancheta-be.azurewebsites.net"

private val base_retrofit_builder = Retrofit.Builder().baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addCallAdapterFactory(PranchetaCallAdapterFactory())

val dataSourceModule = module {
    factory<Retrofit> {
        base_retrofit_builder
            .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor(get())).build())
            .build()
    }

    factory { get<Retrofit>().create(UserEndpoint::class.java) }
    factory { get<Retrofit>().create(ProjectEndpoint::class.java) }
    factory { get<Retrofit>().create(BriefingEndpoint::class.java) }
}

