package com.bejaranix2.materialui.screens.common.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import com.bejaranix2.materialui.databinding.*
import java.lang.IllegalArgumentException

class BindingFactory(private val inflater: LayoutInflater){

    fun <T: ViewDataBinding> newInstance(clazz: Class<T>,@Nullable container: ViewGroup?): T{
        return when(clazz){
            ActivityMainBinding::class.java -> ActivityMainBinding.inflate(inflater)
            FragmentInitialBinding::class.java -> FragmentInitialBinding.inflate(inflater, container, false)
            FragmentNextBinding::class.java -> FragmentNextBinding.inflate(inflater,container,false)
            FragmentViewPagerBinding::class.java -> FragmentViewPagerBinding.inflate(inflater,container,false)
            FragmentAnimationBinding::class.java -> FragmentAnimationBinding.inflate(inflater,container,false)
            FragmentMotionBinding::class.java -> FragmentMotionBinding.inflate(inflater, container, false)
            else -> throw IllegalArgumentException("Binding class does not exists")
        } as T
    }

}