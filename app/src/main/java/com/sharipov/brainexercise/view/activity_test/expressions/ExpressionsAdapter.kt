package com.sharipov.brainexercise.view.activity_test.expressions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.Expression
import kotlinx.android.synthetic.main.shapes_card_item.view.*

class ExpressionsAdapter : RecyclerView.Adapter<ExpressionsAdapter.ExpressionsViewHolder>() {
    lateinit var cardList: List<Expression>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpressionsViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.shapes_card_item, parent, false)
        return ExpressionsViewHolder(view)
    }

    override fun getItemCount(): Int = cardList.size

    override fun onBindViewHolder(holder: ExpressionsViewHolder, position: Int): Unit = with(holder.itemView) {
        taskTextView.text = cardList[position].expression
    }

    fun getAnswer(position: Int): Int = cardList[position].answer

    inner class ExpressionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
