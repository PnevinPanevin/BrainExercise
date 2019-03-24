package com.sharipov.brainexercise.view.test_details

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter: ValueFormatter() {
    private val shortFormat = SimpleDateFormat("dd.MM", Locale.getDefault())

    override fun getFormattedValue(value: Float): String {
        return shortFormat.format(Date(value.toLong()))
    }
}