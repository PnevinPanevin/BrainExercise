package com.sharipov.brainexercise.view.statistics

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class DateXAxisFormatter : IAxisValueFormatter {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String =
        try {
            val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
            sdf.format(Date(value.toLong()))
        } catch (e: Exception) {
            value.toString()
        }
}
