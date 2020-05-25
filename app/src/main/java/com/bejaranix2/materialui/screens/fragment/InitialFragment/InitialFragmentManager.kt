package com.bejaranix2.materialui.screens.fragment.InitialFragment

import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.FragmentInitialBinding
import com.bejaranix2.materialui.model.MsgModel
import com.bejaranix2.materialui.screens.activity.ToolbarEventEnum
import com.bejaranix2.materialui.screens.activity.ToolbarGroup
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import com.bejaranix2.materialui.screens.common.viewmanager.BaseViewManager
import com.bejaranix2.materialui.screens.fragment.BaseFragment
import com.google.android.material.snackbar.Snackbar

class InitialFragmentManager : BaseViewManager {
    private var mInflater: LayoutInflater
    private var mFragment: BaseFragment
    private var mToolbarViewManager: ToolbarViewManager
    private var mFragmentInitialBinding: FragmentInitialBinding

    constructor(fragmentInitialBinding: FragmentInitialBinding, toolbarViewManager: ToolbarViewManager,
                fragment: BaseFragment, inflater: LayoutInflater){
        mFragmentInitialBinding = fragmentInitialBinding
        mToolbarViewManager = toolbarViewManager
        mFragment = fragment
        mInflater = inflater
        listeners()
        viewConfiguration()
        msgAdapter()
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
        mFragmentInitialBinding.root.setOnClickListener {
            val snackbar =Snackbar.make(mFragmentInitialBinding.root, "Hello", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Undo") {
                snackbar.dismiss()
            }
            snackbar.show()
        }
    }

    private fun viewConfiguration() {
        mToolbarViewManager.setToolbarGroup(ToolbarGroup.EXAMPLE)
        mToolbarViewManager.setTitle(mFragment.getString(R.string.initial_fragment_title))
    }

    private fun msgAdapter(){
        val list = arrayListOf(
            MsgModel(true, R.mipmap.descarga,"hola como estas", "Seen at 9:00 pm" ),
            MsgModel(false, R.mipmap.descarga,"hola como estas", "Seen at 9:00 pm" ),
            MsgModel(true, R.mipmap.descarga,"hola como estas", "Seen at 9:00 pm" ),
            MsgModel(false, R.mipmap.descarga,"hola como estas", "Seen at 9:00 pm" ),
            MsgModel(true, R.mipmap.descarga,"hola como estas", "Seen at 9:00 pm" )
        )
        mFragmentInitialBinding.msgRV.apply {
            layoutManager = LinearLayoutManager(mFragment.context)
            adapter = MsgAdapter(list, mInflater)
        }

    }

}