package com.bejaranix2.materialui.screens.activity

import kotlinx.coroutines.flow.MutableStateFlow

interface NavigationViewManager {
   fun getNavigationListener():MutableStateFlow<NavigationEventEnum>
    fun enableNavigationView(boolean: Boolean)
}