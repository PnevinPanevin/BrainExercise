package com.sharipov.brainexercise.view.exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.firebase.Exercise
import com.sharipov.brainexercise.view.test_details.TestDetailsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_exercise_item.view.*

class ExerciseAdapter(private val itemList: List<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_exercise_item, parent, false)
        return ExerciseHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) =
        with(holder.itemView) {
            val currentItem = itemList[position]
            textView.text = currentItem.title
            Picasso.get()
                .load(currentItem.image)
                .into(imageView)
            setOnClickListener {
                val action = R.id.action_exercisesFragment_to_testDetailsFragment
                val args = bundleOf(TestDetailsFragment.TEST_DETAILS to currentItem)
                findNavController().navigate(action, args)
            }
        }

    inner class ExerciseHolder(view: View) : RecyclerView.ViewHolder(view)
}