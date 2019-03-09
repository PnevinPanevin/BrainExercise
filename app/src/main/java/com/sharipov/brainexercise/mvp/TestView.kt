package com.sharipov.brainexercise.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TestView: MvpView {
    fun updateTime(timeLeft: String)
    fun updateScore(score: Int)
    fun updateCountDown(countsLeft: String)
    fun setCountDownVisibility(visibility: Int)
    fun scrollToBeginning()
    fun scrollToPosition(position: Int)
    fun showPauseDialog(score: Int)
    fun showFinishDialog(score: Int)
    fun showLeaveDialog(score: Int)
    fun onBackPressed()
}