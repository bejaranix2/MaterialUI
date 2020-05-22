package com.bejaranix2.materialui.common.di.presentation

import android.app.Activity
import com.bejaranix2.materialui.screens.common.binding.BindingFactory
import dagger.Module
import dagger.Provides

@Module
class BindingModule {

    @Provides
    fun bindingFactory(activity:Activity) =  BindingFactory(activity)

}