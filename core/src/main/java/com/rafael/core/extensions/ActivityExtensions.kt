package com.rafael.core.extensions

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import android.util.Base64OutputStream
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream
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

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_", //prefix
        ".jpg", //suffix
        storageDir //directory
    )
}

fun File.toBase64(maxSize: Int = 1024 * 1024): String {
    val outputStream = ByteArrayOutputStream()
    var bmp = BitmapFactory.decodeFile(this.absolutePath)
    do {
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        Log.d("AAAA", outputStream.toByteArray().size.toString())
        bmp = BitmapFactory.decodeByteArray(outputStream.toByteArray(), 0, outputStream.size())
    } while (outputStream.toByteArray().size > maxSize)

    return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
}

fun String.toBitmap(): ImageBitmap {
    val bytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
}