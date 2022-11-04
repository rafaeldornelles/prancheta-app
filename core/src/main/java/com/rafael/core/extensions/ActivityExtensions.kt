package com.rafael.core.extensions

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.datepicker.MaterialDatePicker
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun Context.showDatePicker(
    date: Long,
    updatedDate: (Long) -> Unit
) {
    val c = Calendar.getInstance().apply {
        time = Date(date)
    }
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val date = c.get(Calendar.DATE)
    val listener = DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
        c.set(Calendar.YEAR, y)
        c.set(Calendar.MONTH, m)
        c.set(Calendar.DATE, d)
        updatedDate(c.time.time)
    }
    val picker = DatePickerDialog(this, listener, year, month, date)
    picker.show()
}

fun Context.createImageFile() : File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_", //prefix
        ".jpg", //suffix
        storageDir //directory
    )
}