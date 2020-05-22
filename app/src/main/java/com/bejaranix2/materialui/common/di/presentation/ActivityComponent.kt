package com.bejaranix2.materialui.common.di.presentation

import com.bejaranix2.materialui.screens.activity.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [ActivityModule::class, BindingModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
    fun newFragmentComponent(fragmentModule: FragmentModule):FragmentComponent
}