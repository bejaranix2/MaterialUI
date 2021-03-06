package com.bejaranix2.materialui.screens.fragment.animationfragment

import android.view.View
import android.view.ViewGroup

abstract class CircleAdapter<T: CircleAdapter.CircleViewHolder> {

    var circleView:CircleView? = null

    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T
    open fun getItemViewType(position: Int): Int = 0
    abstract fun getItemCount(): Int
    abstract fun onBindViewHolder(holder:T, position: Int)
    fun notifyItemInsertedAtEnd() {
        circleView?.addElement()
    }

    abstract class CircleViewHolder(val view: View)

}


