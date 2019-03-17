package com.sharipov.brainexercise.view.test_details

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.mikephil.charting.data.Entry
import com.sharipov.brainexercise.interactor.ResultInteractor
import com.sharipov.brainexercise.mvp.TestDetailsView

@InjectViewState
class TestDetailsPresenter : MvpPresenter<TestDetailsView>() {
    val interactor = ResultInteractor().apply { userId = ResultInteractor.USER_ID }

    fun getStatistics(testName: String) {
        viewState.showProgress()
        interactor.getResults(
            testName,
            { onSuccess(it) },
            { onError(it.message) }
        )
    }

    private fun onSuccess(entries: List<Entry>) {
        viewState.hideProgress()
        viewState.showStatistics(entries)
    }

    private fun onError(message: String) {
        viewState.hideProgress()
        viewState.showError(message)
    }
}