package com.bejaranix2.materialui.screens.activity

import android.R.id.toggle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.flow.MutableStateFlow


class NavigationViewManagerImpl : NavigationViewManager {

    private val mActionBar: ActionBarDrawerToggle
    private val mToolbar: MaterialToolbar
    private var mDrawerLayout: DrawerLayout
    private var mBinding: ActivityMainBinding
    private val navigationListener = MutableStateFlow(NavigationEventEnum.NO_EVENT)

    constructor(binding: ActivityMainBinding, mainActivity: MainActivity){
        mBinding = binding
        mDrawerLayout = mBinding.drawerLayout
        mToolbar = mBinding.toolbar
        mActionBar = ActionBarDrawerToggle(mainActivity,mDrawerLayout,mToolbar, R.string.drawer_open, R.string.drawer_closed)
        mDrawerLayout.addDrawerListener(mActionBar)
        mActionBar.syncState()
        createNavigationListener()
    }

    override fun getNavigationListener(): MutableStateFlow<NavigationEventEnum> {
        return navigationListener
    }

    override fun enableNavigationView(boolean: Boolean) {
        val lockMode =
            if (boolean) DrawerLayout.LOCK_MODE_UNLOCKED else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        mDrawerLayout.setDrawerLockMode(lockMode)
        mActionBar.isDrawerIndicatorEnabled = boolean
    }

    private fun createNavigationListener(){
        mBinding.navigation.setNavigationItemSelectedListener {
            navigationListener.value = NavigationEventEnum.valueOf(it.itemId)
            false
        }
    }

}