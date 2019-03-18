package com.sharipov.brainexercise.view.exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sharipov.brainexercise.R
import com.sharipov.brainexercise.model.firebase.CategoryItem
import com.sharipov.brainexercise.view.test_details.TestDetailsFragment
import com.squareup.picasso.Picasso
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
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
}