package com.rafael.core.cache

import android.content.SharedPreferences
import com.rafael.core.extensions.get
import com.rafael.core.extensions.set

class UserCache(
    private val sharedPreferences: SharedPreferences
) {

    var currentUserId: String?
        get() = sharedPreferences[AUTH_USER_ID_KEY]
        set(value) {
            sharedPreferences[AUTH_USER_ID_KEY] = value
        }

    companion object {
        const val AUTH_USER_ID_KEY = "AUTH_USER_ID"
    }
}