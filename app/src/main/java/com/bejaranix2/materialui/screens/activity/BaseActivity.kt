package com.bejaranix2.materialui.screens.activity

import androidx.appcompat.app.AppCompatActivity
import com.bejaranix2.materialui.application.MainApplication
import com.bejaranix2.materialui.common.di.presentation.ActivityComponent
import com.bejaranix2.materialui.common.di.presentation.ActivityModule
import com.bejaranix2.materialui.common.di.presentation.BindingModule
import java.lang.RuntimeException

abstract class BaseActivity : AppCompatActivity() {

    private var mIsInjectorUsed = false

    private fun getApplicationComponent() = (application as MainApplication).applicationComponent

    fun getActivityComponent(): ActivityComponent{
        if(!mIsInjectorUsed){
            return getApplicationComponent()
                .newActivityModule(ActivityModule(this), BindingModule())
        }else{
            throw RuntimeException("getActivityComponent only can be called once")
        }
    }
}