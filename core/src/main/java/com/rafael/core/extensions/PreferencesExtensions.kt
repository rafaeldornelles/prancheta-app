package com.rafael.core.extensions

import android.content.Context
import android.content.SharedPreferences

inline operator fun <reified T> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
    if (!contains(key)) {
        return null
    }
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        Set::class -> getStringSet(key, defaultValue as? Set<String>) as T?
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> editApplying { putString(key, value) }
        is Int -> editApplying { putInt(key, value) }
        is Boolean -> editApplying { putBoolean(key, value) }
        is Float -> editApplying { putFloat(key, value) }
        is Long -> editApplying { putLong(key, value) }
        null -> removeApplying(key)
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

operator fun SharedPreferences.Editor.set(key: String, value: Any?) {
    when (value) {
        is String? -> putString(key, value)
        is Int -> putInt(key, value)
        is Boolean -> putBoolean(key, value)
        is Float -> putFloat(key, value)
        is Long -> putLong(key, value)
        null -> remove(key)
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.commit()
}

fun SharedPreferences.remove(key: String) {
    val editor = this.edit()
    editor.remove(key)
    editor.apply()
}

inline fun SharedPreferences.editApplying(operation: SharedPreferences.Editor.() -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}

fun SharedPreferences.removeApplying(key: String) {
    val editor = this.edit()
    editor.remove(key)
    editor.apply()
}

fun Context.getPrefs(name: String): SharedPreferences =
    getSharedPreferences(name, Context.MODE_PRIVATE)
