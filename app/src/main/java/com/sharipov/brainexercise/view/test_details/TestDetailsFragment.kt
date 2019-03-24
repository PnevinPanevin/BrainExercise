package com.sharipov.brainexercise.view.test_details

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.devs.readmoreoption.ReadMoreOption
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.snackbar.Snackbar
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.firebase.Exercise
import com.sharipov.brainexercise.model.firebase.TestType
import com.sharipov.brainexercise.mvp.TestDetailsView
import com.sharipov.brainexercise.view.hide
import com.sharipov.brainexercise.view.show
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_test_details.*
import kotlinx.android.synthetic.main.fragment_test_details.view.*
import java.text.SimpleDateFormat
import java.util.*

class TestDetailsFragment : MvpAppCompatFragment(), TestDetailsView {
    companion object {
        const val TEST_DETAILS = "TEST_DETAILS"
        const val HINT = "HINT"
    }

    @InjectPresenter
    lateinit var presenter: TestDetailsPresenter

    lateinit var navController: NavController
    private lateinit var testDetails: Exercise

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        testDetails = arguments?.getSerializable(TEST_DETAILS) as Exercise
        return inflater.inflate(R.layout.fragment_test_details, container, false).apply {
            collapsingToolbar.setupWithNavController(toolbar, navController)
            collapsingToolbar.title = testDetails.title
            Picasso.get()
                .load(testDetails.image)
                .into(imageView)
            startButton.setOnClickListener {
                navController.navigate(
                    getNavId(testDetails.type),
                    bundleOf(HINT to testDetails.hint)
                )
            }
            setupLineChart(chart)
            getStatistics()

            ReadMoreOption.Builder(activity)
                .textLength(6, ReadMoreOption.TYPE_LINE)
                .moreLabel("Читать дальше")
                .lessLabel("Скрыть")
                .moreLabelColor(Color.BLUE)
                .lessLabelColor(Color.BLUE)
                .labelUnderLine(true)
                .expandAnimation(true)
                .build()
                .addReadMoreTo(descriptionTextView, HtmlCompat.fromHtml(testDetails.description, HtmlCompat.FROM_HTML_MODE_COMPACT))
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

        // get the legend (only possible after setting data)
        with(chart.legend) {
            form = Legend.LegendForm.LINE
            textColor = Color.BLACK
        }

        with(chart.xAxis) {
            textColor = Color.BLACK
            isEnabled = true
            valueFormatter = DateFormatter()
            setDrawGridLines(false)
            setAvoidFirstLastClipping(true)
        }

        with(chart.axisLeft) {
            textColor = Color.BLACK
            setDrawGridLines(true)
        }

        val rightAxis = chart.axisRight
        rightAxis.isEnabled = false
    }

    private fun getNavId(type: TestType) = when (type) {
        TestType.EXPRESSIONS -> R.id.action_testDetailsFragment_to_expressionsFragment
        TestType.COMPARISONS -> R.id.action_testDetailsFragment_to_comparisonsFragment
        TestType.SHAPES -> R.id.action_testDetailsFragment_to_shapesFragment
        TestType.PROGRESSIONS -> R.id.action_testDetailsFragment_to_progressionsFragment
        else -> -1
    }

    override fun showStatistics(entries: List<Entry>) {
        val dataSets = ArrayList<ILineDataSet>()
        val results = LineDataSet(entries, "Результат")
        results.lineWidth = 2.5f
        results.circleRadius = 4f

        val rColor = ColorTemplate.VORDIPLOM_COLORS.first()
        results.color = rColor
        results.setCircleColor(rColor)

        dataSets += results

//        val mistakes = LineDataSet(entries.last(), "Ошибки")
//        results.lineWidth = 2.5f
//        results.circleRadius = 4f
//
//        val mColor = ColorTemplate.VORDIPLOM_COLORS[1]
//        results.color = mColor
//        results.setCircleColor(mColor)
//
//        dataSets += mistakes

        val data = LineData(dataSets)
        with(chart) {
            this.data = data
            notifyDataSetChanged()
            moveViewToX(data.entryCount.toFloat())
        }
    }

    override fun showError(message: String) =
        Snackbar.make(scrollView, message, Snackbar.LENGTH_SHORT)
            .setAction("Повторить") { getStatistics() }
            .show()

    private fun getStatistics() = presenter.getStatistics(testDetails.type.name)

    override fun showProgress() = progressBar.show()

    override fun hideProgress() = progressBar.hide()

    override fun onNotEnoughData() {
        notEnoughTextView.visibility = View.VISIBLE
        statisticsTextView.visibility = View.GONE
        chart.visibility = View.GONE
    }
}
