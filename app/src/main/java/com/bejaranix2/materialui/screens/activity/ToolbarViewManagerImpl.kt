package com.bejaranix2.materialui.screens.activity

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.MutableLiveData
import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.ActivityMainBinding
import com.bejaranix2.materialui.screens.common.viewmanager.BaseViewManager


class ToolbarViewManagerImpl : BaseViewManager, ToolbarViewManager{

    private val toolbarLister = MutableLiveData<ToolbarEventEnum>()

    private val navigationListener = MutableLiveData(false)

    private val binding: ActivityMainBinding

    constructor(binding: ActivityMainBinding){
        this.binding = binding
        setRootView(binding.root)
        createItemListener()
        createNavigationListener()
        createSearchListener()
        createSharedListener()
        disableCollapsingToolbar()
    }

    override fun getToolbarListener(): MutableLiveData<ToolbarEventEnum> {
        return toolbarLister
    }

    override fun getNavigationListener(): MutableLiveData<Boolean> {
        return navigationListener
    }

    override fun setTitle(title: String) {
        binding.collapsingToolbar.title = title
        binding.toolbar.title = title
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

    private fun createNavigationListener(){
        binding.toolbar.setNavigationOnClickListener {
            navigationListener.value = true
            navigationListener.value = false
        }
    }

    private fun createSearchListener(){
        val searchView = (binding.toolbar.menu.findItem(R.id.search_item).actionView as SearchView)
        searchView.setOnQueryTextListener( object:SearchView.OnQueryTextListener{
            var action = false

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("onQueryTextSubmit", query)
                if(query == "" && action){
                    searchView.onActionViewCollapsed()
                }
                action = true
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("onQueryTextChange", newText)
                if(newText == "" && action){
                    searchView.onActionViewCollapsed()
                }
                action = true

                return false
            }

        })
        searchView.onActionViewExpanded()
    }

    private fun createSharedListener(){
        val sharedMenu = binding.toolbar.menu.findItem(R.id.share_item).setOnMenuItemClickListener {
            shareInfo()
            false
        }

    }

    private fun disableCollapsingToolbar(){
        binding.appBarLayout.setExpanded(false, true)
        binding.toolbarImage.visibility = View.GONE
    }


    private fun shareInfo(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        getContext()?.apply {
            startActivity(this, shareIntent, null)
        }
    }

}