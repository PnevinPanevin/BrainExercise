package com.sharipov.brainexercise.mvp

import com.arellomobile.mvp.MvpView

interface StatisticsView: MvpView {
    fun showProgress()
    fun hideProgress()
}