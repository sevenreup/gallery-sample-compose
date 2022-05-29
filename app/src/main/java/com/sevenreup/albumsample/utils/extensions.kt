package com.sevenreup.albumsample.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.sevenreup.albumsample.AppPreferences
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


fun Int.toMb(): String {
    val kilo: Long = 1024
    val kb: Double = this.toDouble() / kilo
    return String.format("%.2f", kb / kilo) + " MB";
}

fun String.normaliseDateString(): String {
    val date = ZonedDateTime.parse(this)
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    return formatter.format(date)
}

fun String.getTimeFromDateString(): String {
    val date = ZonedDateTime.parse(this)
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return formatter.format(date)
}

private const val DATA_STORE_FILE_NAME = "app_prefs.pb"

val Context.appPreferencesStore: DataStore<AppPreferences> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = AppPreferencesSerializer
)