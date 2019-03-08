package com.sharipov.brainexercise.util

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class LockableRecyclerView : RecyclerView {
    var isLocked: Boolean = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean = !isLocked && super.onInterceptTouchEvent(e)

    override fun onTouchEvent(e: MotionEvent?): Boolean = !isLocked && super.onTouchEvent(e)
}