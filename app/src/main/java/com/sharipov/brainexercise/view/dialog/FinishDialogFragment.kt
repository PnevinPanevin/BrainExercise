package com.sharipov.brainexercise.view.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.sharipov.brainexercise.model.firebase.TestResult
import kotlinx.android.synthetic.main.fragment_dialog_finish.view.*

class FinishDialogFragment : DialogFragment() {
    lateinit var result: TestResult
    var title: Int = 0
    var positiveText: Int = 0
    var negativeText: Int = 0
    lateinit var positiveAction: () -> Unit
    lateinit var negativeAction: () -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(com.sharipov.brainexercise.R.layout.fragment_dialog_finish, null)
            .apply { initChart(pieChart) }
        isCancelable = false
        return AlertDialog.Builder(activity!!).setView(view)
            .setTitle(title)
            .setPositiveButton(positiveText) { dialog, id -> positiveAction() }
            .setNegativeButton(negativeText) { dialog, id -> negativeAction() }
            .create()
    }

    private fun initChart(chart: PieChart) = with(chart) {
        setUsePercentValues(true)
        description.isEnabled = false
        setExtraOffsets(5F, 20F, 50F, 5F)

        dragDecelerationFrictionCoef = 0.95f

        //chart.setCenterTextTypeface(tfLight)
        centerText = "Ваш результат: ${result.score}"
        //TODO Сделать счет большего размера

        isDrawHoleEnabled = true
        setHoleColor(Color.WHITE)

        setTransparentCircleColor(Color.WHITE)
        setTransparentCircleAlpha(110)

        holeRadius = 56f
        transparentCircleRadius = 61f

        setDrawCenterText(true)

        maxAngle = 180F
        rotationAngle = 180F
        isRotationEnabled = false
        isHighlightPerTapEnabled = false

        animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);

        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        setDrawEntryLabels(false)
        setEntryLabelColor(Color.GRAY)
        setEntryLabelTextSize(12f)

        val entries = ArrayList<PieEntry>()
        val correctAnswers = result.totalAnswers - result.wrongAnswers
        entries.add(PieEntry(correctAnswers.toFloat(), "Правильных") )
        entries.add(PieEntry(result.wrongAnswers.toFloat(), "Неправильных"))

        val dataSet = PieDataSet(entries, "").apply {
            colors = ColorTemplate.JOYFUL_COLORS.asList()
            setDrawIcons(false)
            sliceSpace = 3f
            iconsOffset = MPPointF(0f, 40f)
            selectionShift = 5f
        }

        val data = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter())
            setValueTextSize(11f)
            setValueTextColor(Color.WHITE)
        }

        this.data = data
        highlightValues(null)
        invalidate()
    }
}