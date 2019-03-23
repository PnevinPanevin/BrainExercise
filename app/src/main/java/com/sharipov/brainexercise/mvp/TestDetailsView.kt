package com.sharipov.brainexercise.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.mikephil.charting.data.Entry

@StateStrategyType(AddToEndSingleStrategy::class)
interface TestDetailsView: MvpView {
    fun showProgress()
    fun hideProgress()
    fun showStatistics(entries: List<Entry>)
    fun showError(message: String)
    fun onNotEnoughData()
}