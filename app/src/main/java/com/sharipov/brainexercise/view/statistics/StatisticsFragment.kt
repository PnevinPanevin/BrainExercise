package com.sharipov.brainexercise.view.statistics


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.firebase.TestResult
import com.sharipov.brainexercise.mvp.StatisticsView
import com.sharipov.brainexercise.view.hide
import com.sharipov.brainexercise.view.show
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.android.synthetic.main.fragment_statistics.view.*
import com.github.mikephil.charting.components.XAxis



class StatisticsFragment : MvpAppCompatFragment(), StatisticsView {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.sharipov.brainexercise.R.layout.fragment_statistics, container, false)
            .apply {
                setupLineChart(lineChart)

                FirebaseDatabase.getInstance()
                    .getReference(USER_ID)
                    .child(EXPRESSIONS)
                    .addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val entries = p0.children
                            .mapNotNull {
                                val result = it.getValue<TestResult>(TestResult::class.java)
                                Entry(result?.date?.toFloat() ?: 0F, result?.score?.toFloat() ?: 0F)
                            }
                        showResults(entries)
                    }
                })
            }
    }

    private fun setupLineChart(lineChart: LineChart) = with(lineChart) {
        setBackgroundColor(Color.WHITE)
        setPinchZoom(true)
        setDragEnabled(true)
        setScaleEnabled(true)

        val xAxis = this.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = DateXAxisFormatter()
    }

    fun showResults(entries: List<Entry>){
        val dataSet = LineDataSet(entries, "Label")
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.notifyDataSetChanged()
    }

    override fun showProgress() = progressBar.show()

    override fun hideProgress() = progressBar.hide()

    companion object {
        const val USER_ID = "XiaomiRedmi 3S1552132188809"
        const val EXPRESSIONS = "EXPRESSIONS"
    }

}
