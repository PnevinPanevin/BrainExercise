package com.sharipov.brainexercise.view.test_fragments.shapes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.CardsRepository
import com.sharipov.brainexercise.model.ShapeCard
import com.sharipov.brainexercise.view.test_fragments.TestAdapter
import kotlinx.android.synthetic.main.shapes_card_item.view.*

class ShapesAdapter : RecyclerView.Adapter<ShapesAdapter.CardsViewHolder>(), TestAdapter {

    lateinit var shapeCardList: List<ShapeCard>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shapes_card_item, parent, false)
        return CardsViewHolder(view)
    }

    override fun getItemCount(): Int = shapeCardList.size

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) =
        holder.itemView
            .shapeView
            .setImageResource(shapeCardList[position].shape)

    override fun <T> getAnswer(position: Int): T = shapeCardList[position].answer as T

    override fun resetList() {
        shapeCardList = CardsRepository.getShapeCardList()
        notifyDataSetChanged()
    }

    inner class CardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}