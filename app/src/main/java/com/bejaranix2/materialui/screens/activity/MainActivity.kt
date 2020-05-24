package com.bejaranix2.materialui.screens.activity

import android.os.Bundle
import android.util.Log
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

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent()?.inject(this)
        binding = bindingFactory.newInstance(ActivityMainBinding::class.java, null)
        toolbarViewManager = ToolbarViewManagerImpl(binding)
        setContentView(binding.root)
    }

}
