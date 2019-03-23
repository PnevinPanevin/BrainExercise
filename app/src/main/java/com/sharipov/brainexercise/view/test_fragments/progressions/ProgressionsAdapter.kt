package com.sharipov.brainexercise.view.test_fragments.progressions

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.repository.Progression
import com.sharipov.brainexercise.model.repository.ProgressionsRepository
import com.sharipov.brainexercise.view.test_fragments.TestAdapter
import kotlinx.android.synthetic.main.fragment_progressions_card.view.*

class ProgressionsAdapter: RecyclerView.Adapter<ProgressionsAdapter.ProgressionsHolder>(), TestAdapter {
    lateinit var progressions: List<Progression>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressionsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_progressions_card, parent, false)
        return ProgressionsHolder(view)
    }

    override fun getItemCount(): Int = progressions.size

    override fun onBindViewHolder(holder: ProgressionsHolder, position: Int) = with(holder.itemView){
        val currentItem = progressions[position]
        Log.d("main", currentItem.toString())

        val elements = currentItem.elements
        first.text = elements[0]
        second.text = elements[1]
        third.text = elements[2]
        forth.text = elements[3]
        fifth.text = elements[4]
    }

    override fun <T> getAnswer(position: Int): T = progressions[position].answer as T

    override fun resetList() {
        progressions = ProgressionsRepository.getProgressions()
        notifyDataSetChanged()
    }

    inner class ProgressionsHolder(view: View) : RecyclerView.ViewHolder(view)
}