package com.sevenreup.albumsample.utils

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.sevenreup.albumsample.AppPreferences
import com.sevenreup.albumsample.RequestMessageOptions
import java.io.InputStream
import java.io.OutputStream

val DefaultValues: AppPreferences = AppPreferences.getDefaultInstance().toBuilder()
    .setShareId("djlCbGusTJamg_ca4axEVw")
    .setOptions(RequestMessageOptions.getDefaultInstance().toBuilder()
        .setHeight(160)
        .setWidth(160)
        .setMode("bb")
        .build())
    .build()

object AppPreferencesSerializer : Serializer<AppPreferences> {
    override val defaultValue: AppPreferences = DefaultValues
    override suspend fun readFrom(input: InputStream): AppPreferences {
        try {
            return AppPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }
    override suspend fun writeTo(t: AppPreferences, output: OutputStream) = t.writeTo(output)
}