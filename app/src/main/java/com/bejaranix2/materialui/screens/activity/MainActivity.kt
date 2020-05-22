package com.bejaranix2.materialui.screens.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.ActivityMainBinding
import com.bejaranix2.materialui.screens.common.binding.BindingFactory
import javax.inject.Inject

class MainActivity : BaseActivity(){

    @Inject
    lateinit var bindingFactory : BindingFactory

    lateinit var toolbarViewManager : ToolbarViewManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent()?.inject(this)
        val binding:ActivityMainBinding = bindingFactory.newInstance(ActivityMainBinding::class.java, null)
        toolbarViewManager = ToolbarViewManagerImpl(binding)
        setContentView(binding.root)
    }

}
