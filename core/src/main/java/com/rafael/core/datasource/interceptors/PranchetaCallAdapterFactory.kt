package com.rafael.core.datasource.interceptors

import android.util.Log
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class PranchetaCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType =
                getParameterUpperBound(0, returnType as ParameterizedType)

            when (getRawType(callType)) {
                Result::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    ResultCallAdapter(resultType)
                }

                else -> null
            }
        }

        else -> null
    }
}

class ResultCallAdapter(private val type: Type) : CallAdapter<Type, Call<Result<Type>>> {
    override fun adapt(call: Call<Type>): Call<Result<Type>> = ResultCall(call)
    override fun responseType(): Type = type
}

class ResultCall<T>(private val proxy: Call<T>) : Call<Result<T>> {

    override fun clone(): Call<Result<T>> = ResultCall(proxy.clone())

    override fun enqueue(callback: Callback<Result<T>>) = proxy.enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            Log.e("http", "${response.code()} - ${response.body().toString()}")
            val result = if (response.isSuccessful) {
                mapSuccess(response)
            } else {
                mapError(response)
            }
            try {
                callback.onResponse(this@ResultCall, Response.success(result))
            } catch (e: Throwable) {
                Log.e("http", response.code().toString())
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            Log.e("http", t.message.toString())
            val result = mapFailure(t)
            callback.onResponse(this@ResultCall, Response.success(result))
        }
    })

    override fun timeout(): Timeout {
        return proxy.timeout()
    }

    fun mapSuccess(response: Response<T>): Result<T> = Result.success(response.body()!!)

    fun mapError(response: Response<T>): Result<T> {
        val error = response.errorBody()?.string().toString()
        Log.e("http", response.code().toString())
        Log.e("http", error)
        return Result.failure(CallError(response.code(), error))
    }

    fun mapFailure(t: Throwable): Result<T> = if (t is HttpException) {
        Result.failure(CallError(t.code(), t.message()))
    } else {
        Result.failure(t)
    }

    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()

    override fun execute(): Response<Result<T>> {
        throw NotImplementedError()
    }
}

class CallError(
    val code: Int,
    message: String?
) : Exception(message)