package com.rafael.core.cache

import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.rafael.core.extensions.get
import com.rafael.core.extensions.set
import org.json.JSONObject
import java.util.Date

class TokenCache(val preferences: SharedPreferences) {
    private val _authState: MutableState<Boolean> = mutableStateOf(token != null && is_valid())
    val authState: State<Boolean> get() = _authState

    var token: String?
        get() = preferences[TOKEN_KEY]
        set(value) {
            Log.e("aaaa", "mudando o valor para $value")
            _authState.value = value != null
            preferences[TOKEN_KEY] = value
        }

    var refreshToken: String?
        get() = preferences[REFRESH_TOKEN_KEY]
        set(value) {
            preferences[REFRESH_TOKEN_KEY] = value
        }

    fun clear() {
        token = null
        refreshToken = null
    }

    private fun decoded(): JSONObject? {
        token?.let {
            return JSONObject(String(Base64.decode(it.split(".")[1], Base64.URL_SAFE)))
        }
        return null
    }

    fun is_valid(): Boolean {
        decoded()?.let {
            val iat = it.getInt(IAT_KEY)
            val expiresIn = it.getInt(EXPIRES_IN_KEY)
            val now = Date().time / 1000
            return iat + expiresIn > now
        }
        return false
    }

    private fun is_close_to_expire(): Boolean {
        decoded()?.let {
            val iat = it.getInt(IAT_KEY)
            val expiresIn = it.getInt(EXPIRES_IN_KEY)
            val now = Date().time / 1000
            return iat + expiresIn + 3600 > now
        }
        return false
    }

    fun should_refresh(): Boolean {
        return is_valid() || is_close_to_expire()
    }

    companion object {
        const val TOKEN_KEY = "token"
        const val REFRESH_TOKEN_KEY = "refreshtoken"
        const val IAT_KEY = "iat"
        const val EXPIRES_IN_KEY = "expiresIn"
    }
}