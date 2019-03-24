package com.sharipov.brainexercise.view.test_details

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.interfaces.datasets.IDataSet
import com.google.firebase.auth.FirebaseAuth
import com.sharipov.brainexercise.interactor.ResultInteractor
import com.sharipov.brainexercise.mvp.TestDetailsView

@InjectViewState
class TestDetailsPresenter : MvpPresenter<TestDetailsView>() {
    val interactor = ResultInteractor().apply {
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ResultInteractor.USER_ID
    }

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
        if (entries.size < 3){
            viewState.onNotEnoughData()
        } else {
            viewState.showStatistics(entries)
        }
    }

    private fun onError(message: String) {
        viewState.hideProgress()
        viewState.showError(message)
    }
}