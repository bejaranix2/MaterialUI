package com.bejaranix2.materialui.screens.fragment.animationfragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.FragmentAnimationBinding
import com.bejaranix2.materialui.screens.activity.MainActivity
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import com.bejaranix2.materialui.screens.common.binding.BindingFactory
import com.bejaranix2.materialui.screens.fragment.BaseFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class AnimationFragment : BaseFragment() {

    @Inject
    lateinit var toolbarViewManager: ToolbarViewManager

    @Inject
    lateinit var bindingFactory: BindingFactory

    @Inject
    lateinit var activity: Activity

    @Inject
    lateinit var inflater: LayoutInflater

    lateinit var manager:AnimationManager
    lateinit var flingManager:FlingAnimationManager
    lateinit var binding:FragmentAnimationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getFragmentComponent()?.inject(this)
        binding = bindingFactory.newInstance(FragmentAnimationBinding::class.java, container)
        flingManager = FlingAnimationManager(toolbarViewManager, binding, this, activity)
        manager = AnimationManager(toolbarViewManager, binding, this, inflater)
        return binding.root
    }

}
