package com.bejaranix2.materialui.screens.activity

import androidx.appcompat.app.AppCompatActivity
import com.bejaranix2.materialui.application.MainApplication
import com.bejaranix2.materialui.common.di.presentation.ActivityComponent
import com.bejaranix2.materialui.common.di.presentation.ActivityModule
import com.bejaranix2.materialui.common.di.presentation.BindingModule
import java.lang.RuntimeException

abstract class BaseActivity : AppCompatActivity() {

    var mActivityComponent: ActivityComponent? = null

    private fun getApplicationComponent() = (application as MainApplication).applicationComponent

    fun getActivityComponent(): ActivityComponent?{
        if(mActivityComponent == null){
            mActivityComponent = getApplicationComponent()
                .newActivityModule(ActivityModule(this), BindingModule())
        }
        return mActivityComponent
    }
}