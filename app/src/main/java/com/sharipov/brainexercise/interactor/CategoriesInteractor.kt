package com.sharipov.brainexercise.interactor

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sharipov.brainexercise.model.firebase.CategoryItem
import com.sharipov.brainexercise.model.firebase.Category

class CategoriesInteractor {
    companion object {
        const val CATEGORIES = "categories"
    }

    fun getCategories(
        onError: (DatabaseError) -> Unit,
        onSuccess: (List<Category>) -> Unit
    ) = FirebaseDatabase.getInstance()
        .getReference(CATEGORIES)
        .addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) = onError(p0)

                override fun onDataChange(p0: DataSnapshot) = p0.children
                    .mapNotNull { it.getValue(CategoryItem::class.java) }
                    .groupBy { it.category }
                    .map { Category(it.key, it.value) }
                    .run { onSuccess(this) }
            }
        )
}