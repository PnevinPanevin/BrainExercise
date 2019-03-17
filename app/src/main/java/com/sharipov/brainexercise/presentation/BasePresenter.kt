package com.sharipov.brainexercise.presentation

import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.sharipov.brainexercise.interactor.ResultInteractor
import com.sharipov.brainexercise.interactor.ResultInteractor.Companion.USER_ID
import com.sharipov.brainexercise.model.firebase.TestResult
import com.sharipov.brainexercise.mvp.TestPresenter
import com.sharipov.brainexercise.mvp.TestView
import com.sharipov.brainexercise.util.TestTimer
import com.sharipov.brainexercise.util.formatAsTime
import com.sharipov.brainexercise.view.test_fragments.TestAdapter

@InjectViewState
open class BasePresenter : MvpPresenter<TestView>(), TestPresenter {
    protected var score: Int = 0
    protected var ratio: Int = 1
    protected var currentPosition: Int = 0
    protected var totalAnswers: Int = 0
    protected var wrongAnswers: Int = 0
    lateinit var state: TestPresenter.State
    lateinit var testName: String
    lateinit var testAdapter: TestAdapter
    private val resultInteractor = ResultInteractor().apply { userId = ResultInteractor.USER_ID }

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
        totalAnswers = 0
        wrongAnswers = 0
        score = 0
        currentPosition = 0

        resetList()

        viewState.updateTime(TestPresenter.TEST_DURATION.formatAsTime())
        viewState.updateScore(score)
        viewState.scrollToBeginning()
        viewState.setCountDownVisibility(View.VISIBLE)

        countDownTimer.start()
        state = TestPresenter.State.PREPARED
    }

    override fun onPauseCountDown() {
        countDownTimer.pause()
        state = TestPresenter.State.DELAYED
    }

    override fun onStartTest() {
        viewState.updateCountDown("0")
        viewState.setCountDownVisibility(View.GONE)
        testTimer.start()
        state = TestPresenter.State.STARTED
    }

    override fun onPauseTest() {
        state = TestPresenter.State.PAUSED
        testTimer.pause()
    }

    override fun onResumeTest() {
        testTimer.resume()
        state = TestPresenter.State.STARTED
    }

    override fun onRestartTest() {
        testTimer.reset()
        resetList()
        onPrepareTest()
    }

    override fun onFinishTest() {
        state = TestPresenter.State.FINISHED
        viewState.showFinishDialog(TestResult(testName, System.currentTimeMillis(), score, totalAnswers, wrongAnswers))
    }

    override fun <T> checkAnswer(answer: T) {
        if (answer == testAdapter.getAnswer(currentPosition)) {
            score += 10 * ratio
            if (ratio < 6) {
                ratio++
            }
        } else {
            if (ratio > 1) {
                ratio--
            }
            wrongAnswers++
        }
        viewState.updateScore(score)
        viewState.scrollToPosition(++currentPosition)

        totalAnswers++
    }

    override fun resetList() = testAdapter.resetList()

    override fun onFragmentPause() = when (state) {
        TestPresenter.State.STARTED -> {
            onPauseTest()
            viewState.showPauseDialog(score)
        }
        TestPresenter.State.PREPARED -> onPauseCountDown()
        else -> {}
    }

    override fun onFragmentResume() = when (state) {
        TestPresenter.State.DELAYED -> onRestartTest()
        TestPresenter.State.INTERRUPTED -> onResumeTest()
        else -> {}
    }

    override fun onLeaveTest() {
        when (state) {
            TestPresenter.State.STARTED -> {
                onPauseTest()
                state = TestPresenter.State.INTERRUPTED
            }
            TestPresenter.State.PREPARED -> onPauseCountDown()
            else -> {}
        }
        viewState.showLeaveDialog(score)
    }

    override fun saveResults() {
        val result = TestResult(testName, System.currentTimeMillis(), score, totalAnswers, wrongAnswers)
        resultInteractor.putResults(testName, result)
    }
}