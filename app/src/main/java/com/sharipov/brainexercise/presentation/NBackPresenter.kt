package com.sharipov.brainexercise.presentation

import com.arellomobile.mvp.InjectViewState

@InjectViewState
class NBackPresenter : BasePresenter() {
    override fun onStartTest() {
        super.onStartTest()
        viewState.scrollToPosition(++currentPosition)
    }
}