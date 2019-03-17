package com.sharipov.brainexercise.model.firebase

import java.io.Serializable

data class CategoryItem(
    var title: String = "",
    var image: String = "",
    var description: String = "",
    var type: TestType = TestType.SHAPES,
    var category: String = "",
    var hint: String = ""
) : Serializable