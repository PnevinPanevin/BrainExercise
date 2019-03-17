package com.sharipov.brainexercise.view.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.snackbar.Snackbar
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.firebase.Category
import com.sharipov.brainexercise.mvp.ExercisesView
import com.sharipov.brainexercise.presentation.ExercisesPresenter
import kotlinx.android.synthetic.main.fragment_exercises.*

class ExercisesFragment : MvpAppCompatFragment(), ExercisesView {
    companion object {
        const val TAG = "ExercisesFragment"
    }

    @InjectPresenter
    lateinit var presenter: ExercisesPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getCategories()
    }

    override fun showCategories(categories: List<Category>) = listExercisesRecyclerView.run {
        adapter = CategoryListAdapter(categories)
        layoutManager = LinearLayoutManager(context)
    }

    override fun showError(message: String) =
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT)
            .setAction("Повторить") { presenter.getCategories() }
            .show()
}
