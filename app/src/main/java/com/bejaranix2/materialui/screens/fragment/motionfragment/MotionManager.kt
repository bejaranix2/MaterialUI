package com.bejaranix2.materialui.screens.fragment.motionfragment

import android.util.Log
import android.view.View
import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.FragmentMotionBinding
import com.bejaranix2.materialui.screens.common.viewmanager.BaseViewManager

class MotionManager:BaseViewManager {

    private val mBinding:FragmentMotionBinding

    constructor(binding:FragmentMotionBinding){
        mBinding = binding
        mBinding.manager = this
    }

    fun transitionClick(v: View){
        mBinding.motionLayout.apply {
            Log.d("transitionClic", "transitionClic")
            setTransition(R.id.transition)
            transitionToEnd()
        }
    }
}