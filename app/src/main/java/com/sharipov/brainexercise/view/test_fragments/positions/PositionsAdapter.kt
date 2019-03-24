package com.sharipov.brainexercise.view.test_fragments.positions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.repository.Position
import com.sharipov.brainexercise.model.repository.PositionsGenerator
import com.sharipov.brainexercise.view.test_fragments.TestAdapter
import kotlinx.android.synthetic.main.fragment_positions_item.view.*

class PositionsAdapter : RecyclerView.Adapter<PositionsAdapter.PositionHolder>(), TestAdapter {
    lateinit var positions: List<Position>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_positions_item, parent, false)
        return PositionHolder(view)
    }

    override fun getItemCount(): Int = positions.size

    override fun onBindViewHolder(holder: PositionHolder, position: Int) = with(holder.itemView) {
        val imageViews = listOf<ImageView>(imageView0, imageView1, imageView2, imageView3)
        val currentItem = positions[position]
        imageViews.forEachIndexed { index, imageView ->
            val image = if (currentItem.position == index) {
                R.drawable.ic_filled_circle
            } else {
                R.drawable.ic_empty_circle
            }
            imageView.setImageResource(image)
        }
    }

    override fun <T> getAnswer(position: Int): T = positions[position].answer as T

    override fun resetList() {
        positions = PositionsGenerator.getPositions()
    }

    inner class PositionHolder(view: View) : RecyclerView.ViewHolder(view)
}