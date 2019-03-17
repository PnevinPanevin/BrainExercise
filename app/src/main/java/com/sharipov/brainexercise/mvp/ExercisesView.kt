package com.sharipov.brainexercise.mvp

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sharipov.brainexercise.model.firebase.Category

@StateStrategyType(AddToEndSingleStrategy::class)
interface ExercisesView: MvpView {
    fun showCategories(categories: List<Category>)
    fun showError(message: String)
}
