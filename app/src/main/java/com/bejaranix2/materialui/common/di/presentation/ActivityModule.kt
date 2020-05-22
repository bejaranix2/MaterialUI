package com.bejaranix2.materialui.common.di.presentation

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: FragmentActivity){

    @Provides
    fun activity():Activity = activity

    @Provides
    fun context():Context = activity

}