package com.sevenreup.albumsample.utils


fun Int.toMb(): String {
    val kilo: Long = 1024
    val kb: Double = this.toDouble() / kilo
    return String.format("%.2f", kb / kilo) + " MB";
}