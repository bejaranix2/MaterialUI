package com.bejaranix2.materialui.screens.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.application.MainApplication
import com.bejaranix2.materialui.common.di.presentation.ActivityComponent
import com.bejaranix2.materialui.common.di.presentation.ActivityModule
import com.bejaranix2.materialui.common.di.presentation.BindingModule
import com.bejaranix2.materialui.databinding.ActivityMainBinding
import com.bejaranix2.materialui.screens.common.binding.BindingFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var bindingFactory : BindingFactory

    lateinit var toolbarViewManager : ToolbarViewManager

    lateinit var binding: ActivityMainBinding

    lateinit var navigationViewManager : NavigationViewManager

    var mActivityComponent: ActivityComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent()?.inject(this)
        binding = bindingFactory.newInstance(ActivityMainBinding::class.java, null)
        toolbarViewManager = ToolbarViewManagerImpl(binding)
        navigationViewManager = NavigationViewManagerImpl(binding, this)
        setContentView(binding.root)
    }


    private fun getApplicationComponent() = (application as MainApplication).applicationComponent

    fun getActivityComponent(): ActivityComponent?{
        if(mActivityComponent == null){
            mActivityComponent = getApplicationComponent()
                .newActivityModule(ActivityModule(this), BindingModule())
        }
        return mActivityComponent
    }

}
