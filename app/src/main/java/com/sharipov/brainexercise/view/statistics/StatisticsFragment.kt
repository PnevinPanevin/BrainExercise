package com.sharipov.brainexercise.view.statistics


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.sharipov.brainexercise.interactor.ResultInteractor
import com.sharipov.brainexercise.mvp.StatisticsView
import com.sharipov.brainexercise.view.hide
import com.sharipov.brainexercise.view.show
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.android.synthetic.main.fragment_statistics.view.*
import java.util.*


class StatisticsFragment : MvpAppCompatFragment(), StatisticsView {
    companion object {
        const val TAG = "StatisticsFragment"
        const val EXPRESSIONS = "EXPRESSIONS"
    }

    private val resultInteractor = ResultInteractor().apply { userId = ResultInteractor.USER_ID }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.sharipov.brainexercise.R.layout.fragment_statistics, container, false)
            .apply {
                setupLineChart(chart)
                resultInteractor.getResults(
                    EXPRESSIONS,
                    { showResults(it) },
                    { Log.d(TAG, it.message) }
                )
            }
    }

    private fun setupLineChart(chart: LineChart) {
        with(chart) {
            description.isEnabled = true
            isDragEnabled = true
            setTouchEnabled(true)
            setScaleEnabled(true)
            setDrawGridBackground(false)
            setPinchZoom(false)
        }

        val data = LineData()
        data.setValueTextColor(Color.BLACK)

        chart.data = data // add empty data

        val legend = chart.legend // get the legend (only possible after setting data)
        with(legend) {
            form = Legend.LegendForm.LINE
            textColor = Color.BLACK
        }

        val xl = chart.xAxis
        with(xl) {
            textColor = Color.BLACK
            valueFormatter = DateXAxisFormatter()
            isEnabled = true
            setDrawGridLines(false)
            setAvoidFirstLastClipping(true)
        }

        val leftAxis = chart.axisLeft
        with(leftAxis) {
            textColor = Color.BLACK
            setDrawGridLines(true)
        }

        val rightAxis = chart.getAxisRight()
        rightAxis.isEnabled = false
    }

    fun showResults(entries: List<Entry>) {
        val set = LineDataSet(entries, "Data Set")
        with(set) {
            axisDependency = YAxis.AxisDependency.LEFT
            color = ColorTemplate.getHoloBlue()
            lineWidth = 2f
            circleRadius = 4f
            fillAlpha = 65
            fillColor = ColorTemplate.getHoloBlue()
            highLightColor = Color.rgb(244, 117, 117)
            valueTextColor = Color.BLACK
            valueTextSize = 9f
            setCircleColor(ColorTemplate.COLORFUL_COLORS.last())
            setDrawValues(false)
        }

        val dataSets = ArrayList<ILineDataSet>()
        dataSets += set

        val data = LineData(dataSets)
        with(chart) {
            this.data = data
            notifyDataSetChanged()
            moveViewToX(data.entryCount.toFloat())
        }
    }

    override fun showProgress() = progressBar.show()

    override fun hideProgress() = progressBar.hide()
}
