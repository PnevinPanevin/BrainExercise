package com.sharipov.brainexercise.util

import com.sharipov.brainexercise.R


object Util{
    fun getCategories(): List<Category>{
        val categories = ArrayList<Category>()
        val speed = ArrayList<CategoryItem>()
//        val flex = ArrayList<CategoryItem>()
        val math = ArrayList<CategoryItem>()

        speed += CategoryItem("Shapes", R.drawable.category_item_shapes, R.id.action_exercisesFragment_to_shapesFragment)
//        speed += CategoryItem("Positions", R.drawable.category_item_positions)
        categories += Category("Speed", speed)

//        flex += CategoryItem("Colors", R.drawable.category_item_colors)
//        flex += CategoryItem("Swipes", R.drawable.category_item_swipes)
//        categories += Category("Flexibility", flex)
//
        math += CategoryItem("Expressions", R.drawable.category_item_expressions, R.id.action_exercisesFragment_to_expressionsFragment)
        math += CategoryItem("Comparisons", R.drawable.category_item_comparison, R.id.action_exercisesFragment_to_comparisonsFragment)
//        math += CategoryItem("Progressions", R.drawable.category_item_progressions)
        categories += Category("Math", math)

        return categories
    }
}

data class Category(
    val title: String,
    val itemList: List<CategoryItem>
)

data class CategoryItem(
    val title: String,
    val image: Int,
    val action: Int = 0 //TODO remove default value
)