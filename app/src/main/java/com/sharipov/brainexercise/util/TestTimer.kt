package com.sharipov.brainexercise.util

import android.os.CountDownTimer

class TestTimer(private val durationMillis: Long, private val intervalMillis: Long) {
    init {
        setTimer(durationMillis, intervalMillis)
    }

    var isPaused: Boolean = false
    lateinit var onTick: (l: Long) -> Unit
    lateinit var onFinish: () -> Unit

    private var millisUntilFinished: Long = durationMillis
    private lateinit var timer: CountDownTimer

    fun start() {
        timer.start()
        isPaused = false
    }

    fun pause() {
        if (!isPaused) {
            timer.cancel()
            isPaused = true
        }
    }

    fun resume() {
        if (isPaused) {
            setTimer(millisUntilFinished, intervalMillis)
            timer.start()
            isPaused = false
        }
    }

    fun reset() {
        pause()
        setTimer(durationMillis, intervalMillis)
    }

    private fun setTimer(durationMillis: Long, intervalMillis: Long) {
        timer = object : CountDownTimer(durationMillis, intervalMillis) {
            override fun onTick(millisUntilFinished: Long) {
                this@TestTimer.millisUntilFinished = millisUntilFinished
                onTick.invoke(millisUntilFinished)
            }

            override fun onFinish() = onFinish.invoke()
        }
    }
}