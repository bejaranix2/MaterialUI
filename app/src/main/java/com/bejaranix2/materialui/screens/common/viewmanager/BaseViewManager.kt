package com.bejaranix2.materialui.screens.common.viewmanager

import android.view.View
import androidx.annotation.StringRes

abstract class BaseViewManager:ViewManager {

    var mView:View? = null

    override fun getRootView(): View? {
        return mView
    }

    fun setRootView(view: View){
        mView = view
    }

    fun getContext() = mView?.context

    fun getString(@StringRes id:Int) = getContext()?.getString(id)

    fun getString(@StringRes id:Int, vararg formatArgs:Any ) = getContext()?.getString(id, formatArgs)

   fun <T: View> findView(id: Int):T? = mView?.findViewById(id)
}