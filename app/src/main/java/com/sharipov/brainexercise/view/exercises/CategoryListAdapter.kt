package com.sharipov.brainexercise.view.exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.util.Category
import kotlinx.android.synthetic.main.fragment_exercises_group_item.view.*

class CategoryListAdapter(private val categoryList: List<Category>) :
    RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_exercises_group_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        with(holder.itemView) {
            val currentGroup = categoryList[position]
            title.text = currentGroup.title
            recyclerView.apply {
                adapter = CategoryItemAdapter(currentGroup.itemList)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                hasFixedSize()
            }
        }
    }

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
