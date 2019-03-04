//package com.sharipov.brainexercise.view.exercises
//
//import android.content.Context
//import android.util.Log
//import com.sharipov.brainexercise.R
//
//object CategoryRepository {
//    private val TAG: String = "Category"
//
//    private lateinit var categoryList: MutableList<Category>
//    private lateinit var categoryItemList: MutableList<CategoryItem>
//
//    fun getCategories(context: Context): List<Category> {
//        if (::categoryList.isInitialized) return categoryList
//        else {
//            categoryList = ArrayList()
//            with(context.resources) {
//                val categoryTitles = getStringArray(R.array.category_titles)
//                val itemTitles = getIntArray(R.array.category_item_titles)
//                val itemImages = getIntArray(R.array.category_item_images)
//                for (i in 0 until categoryTitles.size) {
//                    categoryList.add(
//                        Category(categoryTitles[i], getCategoryItemList(context, itemTitles[i], itemImages[i]))
//                    )
//                }
//            }
//        }
//        return categoryList
//    }
//
//    private fun getCategoryItemList(context: Context, titlesId: Int, imagesId: Int): List<CategoryItem> {
//        val result = ArrayList<CategoryItem>()
//        with(context.resources) {
//            val titles = getStringArray(R.array.speed_titles)
//            val images = getIntArray(R.array.speed_images)
//            for (i in 0 until titles.size) result.add(CategoryItem(titles[i], images[i]))
//            result.forEach { Log.d(TAG, "${it.image} ${it.title}") }
//        }
//        return result
//    }
//
//}