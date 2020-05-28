package com.bejaranix2.materialui.screens.fragment.animationfragment

import android.graphics.drawable.AnimatedVectorDrawable
import android.view.View
import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.FragmentAnimationBinding
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import com.bejaranix2.materialui.screens.common.viewmanager.BaseViewManager

class AnimationManager:BaseViewManager {

    private var mToolbarManager: ToolbarViewManager
    private var mFragmentAnimationBinding: FragmentAnimationBinding
    private var mFragment: AnimationFragment
    private lateinit var mAnimation: AnimatedVectorDrawable

    constructor(
        toolbarManager:ToolbarViewManager, fragmentAnimationBinding: FragmentAnimationBinding, fragment: AnimationFragment){
        mToolbarManager = toolbarManager
        mFragment = fragment
        mFragmentAnimationBinding = fragmentAnimationBinding
        mFragmentAnimationBinding.manager = this
        configAnimation()
    }

    private fun configAnimation(){
        mFragmentAnimationBinding.animationImage.apply {
            setBackgroundResource(R.drawable.animation_drawable)
            mAnimation = background as AnimatedVectorDrawable
        }
    }

    fun startAnimation(v: View){
        mAnimation.start()
    }



}