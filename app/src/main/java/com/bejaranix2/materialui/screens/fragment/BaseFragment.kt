package com.bejaranix2.materialui.screens.fragment

import androidx.fragment.app.Fragment
import com.bejaranix2.materialui.common.di.presentation.ActivityComponent
import com.bejaranix2.materialui.common.di.presentation.FragmentComponent
import com.bejaranix2.materialui.common.di.presentation.FragmentModule
import com.bejaranix2.materialui.screens.activity.MainActivity
import java.lang.RuntimeException

abstract class BaseFragment: Fragment() {

    var mFragmentComponent: FragmentComponent? = null

    fun getFragmentComponent():FragmentComponent? {
        if(mFragmentComponent==null) {
            mFragmentComponent = getActivityComponent()
                ?.newFragmentComponent(FragmentModule((activity as MainActivity).toolbarViewManager))
        }
        return mFragmentComponent
    }

    private fun getActivityComponent():ActivityComponent? = (activity as MainActivity).getActivityComponent()

}