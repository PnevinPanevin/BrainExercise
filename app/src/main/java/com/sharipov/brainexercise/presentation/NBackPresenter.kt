package com.sharipov.brainexercise.presentation

abstract class NBackPresenter: BasePresenter() {
    override fun onStartTest() {
        super.onStartTest()
        viewState.scrollToPosition(++currentPosition)
    }
}