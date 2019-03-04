package com.sharipov.brainexercise.presentation

import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.sharipov.brainexercise.model.CardsRepository
import com.sharipov.brainexercise.mvp.TestPresenter
import com.sharipov.brainexercise.mvp.TestPresenter.State.*
import com.sharipov.brainexercise.mvp.TestView
import com.sharipov.brainexercise.util.TestTimer
import com.sharipov.brainexercise.util.formatAsTime
import com.sharipov.brainexercise.view.activity_test.shapes.ShapesAdapter

@InjectViewState
class ShapesPresenter : MvpPresenter<TestView>(), TestPresenter {
    private var score: Int = 0
    private var currentPosition: Int = 0
    lateinit var state: TestPresenter.State

    val shapesAdapter: ShapesAdapter = ShapesAdapter()

    private val countDownTimer: TestTimer =
        TestTimer(TestPresenter.FIRST_COUNTDOWN, TestPresenter.TICK_INTERVAL).apply {
            onTick = { l -> viewState.updateCountDown((l / 1000).toString()) }
            onFinish = { onStartTest() }
        }
    private val testTimer: TestTimer =
        TestTimer(TestPresenter.TEST_DURATION, TestPresenter.TICK_INTERVAL).apply {
            onTick = { l -> viewState.updateTime(l.formatAsTime()) }
            onFinish = { onFinishTest() }
        }

    override fun onPrepareTest() {
        score = 0
        currentPosition = 0

        resetList()

        viewState.updateTime(TestPresenter.TEST_DURATION.formatAsTime())
        viewState.updateScore(score)
        viewState.scrollToBeginning()
        viewState.setCountDownVisibility(View.VISIBLE)

        countDownTimer.start()
        state = PREPARED
    }

    override fun onPauseCountDown() {
        countDownTimer.pause()
        state = DELAYED
    }

    override fun onStartTest() {
        viewState.updateCountDown("0")
        viewState.setCountDownVisibility(View.GONE)
        viewState.scrollToPosition(++currentPosition)
        testTimer.start()
        state = STARTED
    }

    override fun onPauseTest() {
        state = PAUSED
        testTimer.pause()
    }

    override fun onResumeTest() {
        testTimer.resume()
        state = STARTED
    }

    override fun onRestartTest() {
        testTimer.reset()
        resetList()
        onPrepareTest()
    }

    override fun onFinishTest() {
        state = FINISHED
        viewState.showFinishDialog(score)
    }

    override fun <Answer> checkAnswer(answer: Answer) {
        val currentScore = score
        if (answer == shapesAdapter.getAnswer(currentPosition))
            score = currentScore + 1
        else if (currentScore > 0)
            score = currentScore - 1

        viewState.updateScore(score)
        viewState.scrollToPosition(++currentPosition)
    }

    private fun resetList() {
        shapesAdapter.shapeCardList = CardsRepository.getShapeCardList()
        shapesAdapter.notifyDataSetChanged()
    }

    override fun onLeaveTest() {
        when (state) {
            STARTED -> {
                onPauseTest()
                state = INTERRUPTED
            }
            PREPARED -> onPauseCountDown()
        }
        viewState.showLeaveDialog(score)
    }

    override fun onFragmentPause(){
        when (state){
            STARTED -> {
                onPauseTest()
                viewState.showPauseDialog(score)
            }
            PREPARED -> onPauseCountDown()
        }
    }

    override fun onFragmentResume(){
        when(state){
            DELAYED -> onRestartTest()
            INTERRUPTED -> onResumeTest()
        }
    }
}