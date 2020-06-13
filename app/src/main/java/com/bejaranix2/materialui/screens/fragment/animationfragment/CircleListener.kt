package com.bejaranix2.materialui.screens.fragment.animationfragment

interface CircleListener {
    fun deletedElementListener(index: Int, action: CircleView.DeleteAction):Boolean
    fun currentSpinningPositionListener(current: Int)
    fun stoppedSpinListener(current: Int)
}