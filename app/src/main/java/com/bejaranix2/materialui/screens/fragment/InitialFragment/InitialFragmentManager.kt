package com.bejaranix2.materialui.screens.fragment.InitialFragment

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.FragmentInitialBinding
import com.bejaranix2.materialui.screens.activity.ToolbarEventEnum
import com.bejaranix2.materialui.screens.activity.ToolbarGroup
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import com.bejaranix2.materialui.screens.common.viewmanager.BaseViewManager
import com.bejaranix2.materialui.screens.fragment.BaseFragment

class InitialFragmentManager : BaseViewManager {
    private var mFragment: BaseFragment
    private var mToolbarViewManager: ToolbarViewManager
    private var mFragmentInitialBinding: FragmentInitialBinding

    constructor(fragmentInitialBinding: FragmentInitialBinding, toolbarViewManager: ToolbarViewManager, fragment: BaseFragment){
        mFragmentInitialBinding = fragmentInitialBinding
        mToolbarViewManager = toolbarViewManager
        mFragment = fragment
        listeners()
        viewConfiguration()
    }

    private fun listeners(){
        mToolbarViewManager.getNavigationListener().observe(mFragment.viewLifecycleOwner, Observer {
            if (it) {
                NavHostFragment.findNavController(mFragment)
                    .navigate(InitialFragmentDirections.actionInitialFragmentToNextFragment())
            }
        })

        mToolbarViewManager.getToolbarListener().observe(mFragment.viewLifecycleOwner, Observer {
            if(it == ToolbarEventEnum.EXAMPLE_ITEM){
                Toast.makeText(mFragment.context, "Example clicked", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun viewConfiguration() {
        mToolbarViewManager.setToolbarGroup(ToolbarGroup.EXAMPLE)
        mToolbarViewManager.setTitle(mFragment.getString(R.string.initial_fragment_title))
    }

}