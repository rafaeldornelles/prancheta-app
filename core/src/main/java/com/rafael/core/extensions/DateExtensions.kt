package com.rafael.core.extensions

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun Long.toDaysAgo(): String {
    val now = System.currentTimeMillis()
    return DateUtils.getRelativeTimeSpanString(this, now, DateUtils.DAY_IN_MILLIS).toString()
}

fun Long.formatDate(pattern: String = "dd/MM/yyyy", locale: Locale = Locale.getDefault()) : String {
    val df = SimpleDateFormat(pattern, locale)
    val date = Date(this)
    return df.format(date)
}

fun String.parsedate(pattern: String = "dd/MM/yyyy", locale: Locale = Locale.getDefault()): Date {
    val df = SimpleDateFormat(pattern, locale)
    return df.parse(this)
}