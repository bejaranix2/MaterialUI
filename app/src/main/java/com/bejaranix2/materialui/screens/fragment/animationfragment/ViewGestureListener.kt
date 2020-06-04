package com.bejaranix2.materialui.screens.fragment.animationfragment

import android.view.GestureDetector
import android.view.MotionEvent

class ViewGestureListener: GestureDetector.SimpleOnGestureListener() {

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return super.onFling(e1, e2, velocityX, velocityY)
    }


    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return super.onScroll(e1, e2, distanceX, distanceY)
    }


}