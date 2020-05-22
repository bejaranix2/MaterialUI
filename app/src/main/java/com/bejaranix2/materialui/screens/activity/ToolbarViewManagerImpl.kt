package com.bejaranix2.materialui.screens.activity

import androidx.lifecycle.MutableLiveData
import com.bejaranix2.materialui.databinding.ActivityMainBinding
import com.bejaranix2.materialui.screens.common.viewmanager.BaseViewManager

class ToolbarViewManagerImpl : BaseViewManager, ToolbarViewManager{

    private val toolbarLister = MutableLiveData<ToolbarEventEnum>()

    private val binding: ActivityMainBinding

    constructor(binding: ActivityMainBinding){
        this.binding = binding
        setRootView(binding.root)
        createItemListener()
    }

    override fun getToolbarListener(): MutableLiveData<ToolbarEventEnum> {
        return toolbarLister
    }

    override fun setToolbarGroup(vararg toolbarGroup: ToolbarGroup) {
        hideOtherGroups()
        for(group in toolbarGroup) {
            binding.toolbar.menu.setGroupVisible(group.group, true)
        }
    }

    private fun hideOtherGroups(){
        for(group in ToolbarGroup.values()){
            binding.toolbar.menu.setGroupVisible(group.group, false)
        }
    }

    private fun createItemListener(){
        binding.toolbar.setOnMenuItemClickListener{ it ->
            toolbarLister.value = ToolbarEventEnum.valueOf(it.itemId)
            true
        }
    }

}