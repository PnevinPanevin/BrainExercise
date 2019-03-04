package com.sharipov.brainexercise.view.activity_test.shapes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.Answer
import com.sharipov.brainexercise.model.ShapeCard
import kotlinx.android.synthetic.main.shapes_card_item.view.*

class ShapesAdapter : RecyclerView.Adapter<ShapesAdapter.CardsViewHolder>() {
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

    fun getAnswer(position: Int): Answer = shapeCardList[position].answer

    inner class CardsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}