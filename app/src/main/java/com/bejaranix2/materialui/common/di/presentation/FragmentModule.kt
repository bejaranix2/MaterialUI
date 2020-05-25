package com.bejaranix2.materialui.common.di.presentation

import android.util.Log
import com.bejaranix2.materialui.screens.activity.NavigationViewManager
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val toolbarViewManager: ToolbarViewManager, private val navigationViewManager: NavigationViewManager) {

    @Provides
    fun toolbarViewManager(): ToolbarViewManager {
        return toolbarViewManager
    }

    @Provides
    fun navigationViewManager(): NavigationViewManager{
        return navigationViewManager
    }

}