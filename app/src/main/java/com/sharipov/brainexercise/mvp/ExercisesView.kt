package com.sharipov.brainexercise.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sharipov.brainexercise.model.firebase.Exercise

@StateStrategyType(AddToEndSingleStrategy::class)
interface ExercisesView: MvpView, ProgressView {
    fun showCategories(categories: List<Exercise>)
    fun showError(message: String)
}
