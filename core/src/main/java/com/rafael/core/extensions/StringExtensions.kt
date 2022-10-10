package com.rafael.core.extensions

import android.util.Patterns
import java.util.regex.Pattern

fun String.isValidEmail() : Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}