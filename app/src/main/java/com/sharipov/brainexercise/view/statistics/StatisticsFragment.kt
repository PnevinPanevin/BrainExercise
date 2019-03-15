package com.sharipov.brainexercise.view.statistics


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sharipov.brainexercise.model.firebase.TestResult
import com.sharipov.brainexercise.mvp.StatisticsView
import com.sharipov.brainexercise.view.hide
import com.sharipov.brainexercise.view.show
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.android.synthetic.main.fragment_statistics.view.*
import java.util.*


class StatisticsFragment : MvpAppCompatFragment(), StatisticsView {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.sharipov.brainexercise.R.layout.fragment_statistics, container, false)
            .apply {
                setupLineChart(barChart)

                FirebaseDatabase.getInstance()
                    .getReference(USER_ID)
                    .child(EXPRESSIONS)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val entries = p0.children
                                .mapNotNull {
                                    val result = it.getValue<TestResult>(TestResult::class.java)
                                    BarEntry(result?.date?.toFloat()?.div(1000) ?: 100F, result?.score?.toFloat() ?: 100F)
                                }
                            showResults(entries)
                        }
                    })
            }
    }

    private fun setupLineChart(chart: BarChart) = with(chart) {
        //        setBackgroundColor(Color.WHITE)
//        setPinchZoom(false)
//        setDragEnabled(true)
//        setScaleEnabled(true)
//        setDrawGridBackground(false)
//
//        val xAxis = this.xAxis
//        xAxis.position = XAxis.XAxisPosition.BOTTOM
//        xAxis.valueFormatter = DateXAxisFormatter()
        chart.description.isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)

        chart.setDrawBarShadow(false)
        chart.setDrawGridBackground(false)

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)

        chart.axisLeft.setDrawGridLines(false)


        // add a nice and smooth animation
        chart.animateY(1500)

        chart.legend.isEnabled = false
    }

    fun showResults(entries: List<BarEntry>) {
//        val dataSet = LineDataSet(entries, "Label")
//        val lineData = LineData(dataSet)
//        lineChart.data = lineData.also { it.notifyDataChanged() }
//        lineChart.notifyDataSetChanged()

        val values = ArrayList<BarEntry>()

        for (i in 0 until 100) {
            val multi = 100.toFloat()
            val `val` = (Math.random() * multi).toFloat() + multi / 3
            values.add(BarEntry(i.toFloat(), `val`))
        }

        val set1 = BarDataSet(entries, "Data Set")

        set1.setColors(*ColorTemplate.VORDIPLOM_COLORS)
        set1.setDrawValues(false)

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)

        val data = BarData(dataSets)
        barChart.setData(data)
        barChart.setFitBars(true)

        barChart.invalidate()
    }

    override fun showProgress() = progressBar.show()

    override fun hideProgress() = progressBar.hide()

    companion object {
        const val USER_ID = "XiaomiRedmi 3S1552132188809"
        const val EXPRESSIONS = "EXPRESSIONS"
    }

}
