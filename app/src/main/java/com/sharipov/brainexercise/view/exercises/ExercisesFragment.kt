package com.sharipov.brainexercise.view.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.util.Util
import kotlinx.android.synthetic.main.fragment_exercises.*

class ExercisesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(listExercisesRecyclerView) {
            adapter = CategoryListAdapter(Util.getCategories())
            layoutManager = LinearLayoutManager(context)
            //addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }
    }
}
