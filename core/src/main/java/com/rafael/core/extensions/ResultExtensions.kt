package com.rafael.core.extensions

fun <T, O> Result<T>.mapSuccess(block: (T) -> O): Result<O> {
    this.getOrNull()?.let {
        return Result.success(block(it))
    }
    return this as Result<O>
}