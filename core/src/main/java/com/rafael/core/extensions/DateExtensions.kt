package com.rafael.core.extensions

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toDaysAgo(): String {
    val now = System.currentTimeMillis()
    return DateUtils.getRelativeTimeSpanString(this.time, now, DateUtils.DAY_IN_MILLIS).toString()
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