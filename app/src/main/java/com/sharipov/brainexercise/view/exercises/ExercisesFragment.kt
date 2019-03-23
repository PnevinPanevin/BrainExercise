package com.sharipov.brainexercise.view.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.firebase.Exercise
import com.sharipov.brainexercise.mvp.ExercisesView
import com.sharipov.brainexercise.presentation.ExercisesPresenter
import com.sharipov.brainexercise.view.hide
import com.sharipov.brainexercise.view.show
import kotlinx.android.synthetic.main.fragment_exercises.*
import kotlinx.android.synthetic.main.fragment_exercises.view.*

class ExercisesFragment : MvpAppCompatFragment(), ExercisesView {
    companion object {
        const val TAG = "ExercisesFragment"
    }

    @InjectPresenter
    lateinit var presenter: ExercisesPresenter

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false).apply {
            toolbar.setupWithNavController(findNavController())
        }
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null){
            auth.signInAnonymously()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getCategories()
    }

    override fun showCategories(categories: List<Exercise>) = listExercisesRecyclerView.run {
        adapter = ExerciseAdapter(categories)
        layoutManager = LinearLayoutManager(context)
    }

    override fun showError(message: String) =
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT)
            .setAction("Повторить") { presenter.getCategories() }
            .show()

    override fun showProgress() = progressBar.show()

    override fun hideProgress() = progressBar.hide()
}
