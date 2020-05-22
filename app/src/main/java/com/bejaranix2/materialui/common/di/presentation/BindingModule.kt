package com.bejaranix2.materialui.common.di.presentation

import android.app.Activity
import android.view.LayoutInflater
import com.bejaranix2.materialui.screens.common.binding.BindingFactory
import dagger.Module
import dagger.Provides

@Module
class BindingModule {

    @Provides
    fun bindingFactory(inflater:LayoutInflater) =  BindingFactory(inflater)

}