package com.sharipov.brainexercise.view.test_details

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.firebase.CategoryItem
import com.sharipov.brainexercise.model.firebase.TestType
import com.sharipov.brainexercise.mvp.TestDetailsView
import com.sharipov.brainexercise.view.hide
import com.sharipov.brainexercise.view.show
import com.sharipov.brainexercise.view.statistics.DateXAxisFormatter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_test_details.*
import kotlinx.android.synthetic.main.fragment_test_details.view.*
import java.util.*

class TestDetailsFragment : MvpAppCompatFragment(), TestDetailsView {
    companion object {
        const val TEST_DETAILS = "TEST_DETAILS"
    }

    @InjectPresenter
    lateinit var presenter: TestDetailsPresenter

    lateinit var navController: NavController
    private lateinit var testDetails: CategoryItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        testDetails = arguments?.getSerializable(TEST_DETAILS) as CategoryItem
        return inflater.inflate(R.layout.fragment_test_details, container, false).apply {

            collapsingToolbar.setupWithNavController(toolbar, navController)
            collapsingToolbar.title = testDetails.title

            Picasso.get()
                .load(testDetails.image)
                .into(imageView)
            descriptionTextView.text = testDetails.description
            startButton.setOnClickListener { navController.navigate(getNavId(testDetails.type)) }
            setupLineChart(chart)
            getStatistics()
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

    private fun getNavId(type: TestType) = when (type) {
        TestType.EXPRESSIONS -> R.id.action_testDetailsFragment_to_expressionsFragment
        TestType.COMPARISONS -> R.id.action_testDetailsFragment_to_comparisonsFragment
        TestType.SHAPES -> R.id.action_testDetailsFragment_to_shapesFragment
    }

    override fun showStatistics(entries: List<Entry>) {
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

    override fun showError(message: String) =
        Snackbar.make(scrollView, message, Snackbar.LENGTH_SHORT)
            .setAction("Повторить") { getStatistics() }
            .show()

    private fun getStatistics() = presenter.getStatistics(testDetails.type.name)

    override fun showProgress() = progressBar.show()

    override fun hideProgress() = progressBar.hide()
}
