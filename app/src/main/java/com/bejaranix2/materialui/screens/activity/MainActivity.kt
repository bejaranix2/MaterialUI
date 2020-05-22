package com.bejaranix2.materialui.screens.activity

import android.os.Bundle
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
        setContentView(R.layout.activity_main)
        getActivityComponent().inject(this)
        toolbarViewManager = ToolbarViewManagerImpl(bindingFactory.newInstance(ActivityMainBinding::class.java))
        toolbarViewManager.setToolbarGroup(ToolbarGroup.EXAMPLE)
        toolbarViewManager.getToolbarListener().observe(this, Observer {
            when(it){
                ToolbarEventEnum.EXAMPLE_ITEM ->         toolbarViewManager.setToolbarGroup(ToolbarGroup.ANOTHER_EXAMPLE)
                ToolbarEventEnum.ANOTHER_EXAMPLE_ITEM -> toolbarViewManager.setToolbarGroup(ToolbarGroup.EXAMPLE)
            }
        } )

    }

}
