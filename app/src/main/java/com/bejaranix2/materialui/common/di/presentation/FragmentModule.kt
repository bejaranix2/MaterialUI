package com.bejaranix2.materialui.common.di.presentation

import android.util.Log
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val toolbarViewManager: ToolbarViewManager) {

    @Provides
    fun toolbarViewManager(): ToolbarViewManager {
        return toolbarViewManager
    }

}