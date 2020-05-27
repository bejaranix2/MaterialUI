package com.bejaranix2.materialui.common.di.presentation

import com.bejaranix2.materialui.screens.fragment.initialfragment.InitialFragment
import com.bejaranix2.materialui.screens.fragment.nextfragment.NextFragment
import com.bejaranix2.materialui.screens.fragment.viewpagerfragment.ViewPagerFragment
import dagger.Subcomponent

@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(initialFragment: InitialFragment)
    fun inject(nextFragment: NextFragment)
    fun inject(viewPagerFragment: ViewPagerFragment)
}