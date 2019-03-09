package com.sharipov.brainexercise.presentation

import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.sharipov.brainexercise.model.ComparisonsRepository
import com.sharipov.brainexercise.mvp.TestPresenter
import com.sharipov.brainexercise.mvp.TestView
import com.sharipov.brainexercise.util.TestTimer
import com.sharipov.brainexercise.util.formatAsTime
import com.sharipov.brainexercise.view.activity_test.comparisons.ComparisonsAdapter

@InjectViewState
class ComparisonsPresenter : MvpPresenter<TestView>(), TestPresenter {
    private var score: Int = 0
    private var currentPosition: Int = 0
    lateinit var state: TestPresenter.State

    val comparisonsAdapter: ComparisonsAdapter = ComparisonsAdapter()

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
        state = TestPresenter.State.PREPARED
    }

    override fun resetList() {
        comparisonsAdapter.comparisons = ComparisonsRepository.getCardList()
        comparisonsAdapter.notifyDataSetChanged()
    }

    override fun onPauseCountDown() {
        countDownTimer.pause()
        state = TestPresenter.State.DELAYED
    }

    override fun onStartTest() {
        viewState.updateCountDown("0")
        viewState.setCountDownVisibility(View.GONE)
        //viewState.scrollToPosition(++currentPosition)
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
        viewState.showFinishDialog(score)
    }

    override fun <ComparisonAnswer> checkAnswer(answer: ComparisonAnswer) {
        val currentScore = score
        if (answer == comparisonsAdapter.getAnswer(currentPosition))
            score = currentScore + 1
        else if (currentScore > 0)
            score = currentScore - 1

        viewState.updateScore(score)
        viewState.scrollToPosition(++currentPosition)
    }

    override fun onFragmentPause() {
        when (state) {
            TestPresenter.State.STARTED -> {
                onPauseTest()
                viewState.showPauseDialog(score)
            }
            TestPresenter.State.PREPARED -> onPauseCountDown()
        }
    }

    override fun onFragmentResume() {
        when (state) {
            TestPresenter.State.DELAYED -> onRestartTest()
            TestPresenter.State.INTERRUPTED -> onResumeTest()
        }
    }

    override fun onLeaveTest() {
        when (state) {
            TestPresenter.State.STARTED -> {
                onPauseTest()
                state = TestPresenter.State.INTERRUPTED
            }
            TestPresenter.State.PREPARED -> onPauseCountDown()
        }
        viewState.showLeaveDialog(score)
    }
}
