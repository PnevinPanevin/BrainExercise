package com.sharipov.brainexercise.mvp

interface TestPresenter {
    companion object CONST {
        const val TEST_DURATION = 8000L
        const val FIRST_COUNTDOWN = 4000L
        const val TICK_INTERVAL = 200L;
    }

    enum class State {
        PREPARED, DELAYED, STARTED, PAUSED, INTERRUPTED, FINISHED
    }

    fun onPrepareTest()
    fun onPauseCountDown()
    fun onStartTest()
    fun onPauseTest()
    fun onResumeTest()
    fun onRestartTest()
    fun onFinishTest()
    fun <T> checkAnswer(answer: T)
    fun onFragmentPause()
    fun onFragmentResume()
    fun onLeaveTest()
    fun resetList()
    fun saveResults()
}