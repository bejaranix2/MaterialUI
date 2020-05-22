package com.bejaranix2.materialui.screens.common.binding

import android.app.Activity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.ActivityMainBinding
import java.lang.IllegalArgumentException

class BindingFactory(private val activity:Activity){

    private val layout = mapOf(
        ActivityMainBinding::class.java to R.layout.activity_main
    )

    fun <T: ViewDataBinding> newInstance(clazz: Class<T>): T{
        for(pair in layout){
            val layoutVal = pair.value
            if(clazz == pair.key){
                return DataBindingUtil.setContentView(activity, layoutVal)
            }
        }
        throw IllegalArgumentException("Binding class does not exists")
    }

}