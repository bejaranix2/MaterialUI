package com.bejaranix2.materialui.common.di.presentation

import android.app.Application
import com.bejaranix2.materialui.common.di.application.ApplicationComponent
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import com.bejaranix2.materialui.screens.fragment.InitialFragment.InitialFragment
import com.bejaranix2.materialui.screens.fragment.NextFragment.NextFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent

@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(initialFragment: InitialFragment)
    fun inject(nextFragment: NextFragment)

}