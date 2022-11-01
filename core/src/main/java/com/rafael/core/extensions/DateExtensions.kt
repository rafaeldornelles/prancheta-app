package com.rafael.core.extensions

import android.text.format.DateUtils
import java.util.*

fun Long.toDaysAgo(): String {
    val now = System.currentTimeMillis()
    return DateUtils.getRelativeTimeSpanString(this, now, DateUtils.DAY_IN_MILLIS).toString()
}
