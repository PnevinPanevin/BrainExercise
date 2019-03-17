package com.sharipov.brainexercise.model.firebase

data class Category(
    var title: String = "",
    var itemList: List<CategoryItem> = emptyList()
)

