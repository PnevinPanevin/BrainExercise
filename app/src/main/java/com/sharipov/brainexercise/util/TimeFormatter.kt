package com.sharipov.brainexercise.util

fun Long.formatAsTime(): String {
    val minutes = this / 60000
    val seconds = this / 1000
    val secondsToString = if (seconds < 10) "0$seconds" else "$seconds"

    return "$minutes:$secondsToString"
}