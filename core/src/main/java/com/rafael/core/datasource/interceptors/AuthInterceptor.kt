package com.rafael.core.datasource.interceptors

import android.util.Log
import com.rafael.core.cache.TokenCache
import okhttp3.Interceptor
import okio.Buffer

class AuthInterceptor(
    private val tokenCache: TokenCache
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        Log.e("http", "${chain.request().method().toString()} ${chain.request().url().toString()}")
        val buffer = Buffer()
        chain.request().body()?.writeTo(buffer)
        Log.e("http", buffer.readUtf8())
        val builder = chain.request().newBuilder()
        tokenCache.token?.let {
            builder.removeHeader(AUTHORIZATION_HEADER)
            builder.addHeader(AUTHORIZATION_HEADER, it)
        }

        return chain.proceed(builder.build())
    }

    companion object {
        const val AUTHORIZATION_HEADER = "authorization"
    }
}