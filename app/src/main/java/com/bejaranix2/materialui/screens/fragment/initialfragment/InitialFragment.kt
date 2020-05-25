package com.bejaranix2.materialui.screens.fragment.initialfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope

import com.bejaranix2.materialui.databinding.FragmentInitialBinding
import com.bejaranix2.materialui.screens.activity.NavigationEventEnum
import com.bejaranix2.materialui.screens.activity.NavigationViewManager
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import com.bejaranix2.materialui.screens.common.binding.BindingFactory
import com.bejaranix2.materialui.screens.fragment.BaseFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class InitialFragment : BaseFragment() {

    @Inject
    lateinit var toolbarViewManager: ToolbarViewManager

    @Inject
    lateinit var navigationViewManager: NavigationViewManager

    @Inject
    lateinit var bindingFactory: BindingFactory

    @Inject
    lateinit var inflater: LayoutInflater

    private lateinit var fragmentInitialBinding: FragmentInitialBinding

    private lateinit var initialFragmentManager: InitialFragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getFragmentComponent()?.inject(this)
        fragmentInitialBinding = FragmentInitialBinding.inflate(inflater, container, false)
        return fragmentInitialBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialFragmentManager = InitialFragmentManager(fragmentInitialBinding, toolbarViewManager, this, inflater)
        lifecycleScope.launch {
            navigationViewManager.getNavigationListener().collect {
                when(it){
                    NavigationEventEnum.CONFIG_ITEM -> {
                        navigationViewManager.enableNavigationView(false)
                        Log.d("CONFIG_ITEM","???")
                    }
                }
            }
        }

    }

}
