package com.sharipov.brainexercise.util

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class LockableViewPager : ViewPager {
    var isLocked: Boolean = false

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return !isLocked && super.onInterceptTouchEvent(ev)
    }
}