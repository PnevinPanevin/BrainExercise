package com.sharipov.brainexercise.view.exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.util.CategoryItem
import kotlinx.android.synthetic.main.list_exercise_item.view.*

class CategoryItemAdapter(val itemList: List<CategoryItem>) :
    RecyclerView.Adapter<CategoryItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_exercise_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder.itemView) {
            val currentItem = itemList[position]
            imageView.setImageResource(currentItem.image)
            textView.text = currentItem.title
            setOnClickListener { findNavController().navigate(currentItem.action) }
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
}