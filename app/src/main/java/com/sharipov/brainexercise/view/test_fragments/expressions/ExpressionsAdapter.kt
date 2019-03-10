package com.sharipov.brainexercise.view.test_fragments.expressions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.repository.Expression
import com.sharipov.brainexercise.model.repository.ExpressionsRepository
import com.sharipov.brainexercise.view.test_fragments.TestAdapter
import kotlinx.android.synthetic.main.shapes_card_item.view.*

class ExpressionsAdapter : RecyclerView.Adapter<ExpressionsAdapter.ExpressionsViewHolder>(), TestAdapter {
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

    override fun <T> getAnswer(position: Int): T = cardList[position].answer as T

    override fun resetList() {
        cardList = ExpressionsRepository.getCardList()
        notifyDataSetChanged()
    }

    inner class ExpressionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
