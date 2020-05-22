package com.bejaranix2.materialui.screens.activity

import androidx.lifecycle.MutableLiveData

interface ToolbarViewManager {

    fun getToolbarListener():MutableLiveData<ToolbarEventEnum>

    fun setToolbarGroup(vararg toolbarGroup: ToolbarGroup)
}