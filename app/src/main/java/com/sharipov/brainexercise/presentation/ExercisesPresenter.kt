package com.sharipov.brainexercise.presentation

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.sharipov.brainexercise.interactor.CategoriesInteractor
import com.sharipov.brainexercise.mvp.ExercisesView

@InjectViewState
class ExercisesPresenter : MvpPresenter<ExercisesView>() {

    private val interactor = CategoriesInteractor()

    fun getCategories() {
        viewState.showProgress()
        interactor.getCategories(
            {
                viewState.hideProgress()
                viewState.showError(it.message)
            },
            {
                viewState.hideProgress()
                viewState.showCategories(it)
            }
        )
    }
}