package com.bejaranix2.materialui.screens.fragment.viewpagerfragment

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.common.adapter.ZoomOutPageTransformer
import com.bejaranix2.materialui.databinding.FragmentViewPagerBinding
import com.bejaranix2.materialui.screens.common.viewmanager.BaseViewManager

class ViewPagerManager: BaseViewManager {


    private val mBinding: FragmentViewPagerBinding
    private val mFragment: ViewPagerFragment
    private val mInflater:LayoutInflater

    constructor(binding: FragmentViewPagerBinding, fragment: ViewPagerFragment, inflater: LayoutInflater){
        mBinding = binding
        mFragment = fragment
        mInflater = inflater
        mBinding.manager = this
        configViewPager()
    }

    private fun configViewPager(){
        val adapter = ViewPagerAdapter(mFragment.lifecycleScope, mInflater)
        mBinding.viewPager.adapter = adapter
        adapter.list.value = arrayOf(mFragment.getString(R.string.whole_text)
            , ""
            ,mFragment.getString(R.string.whole_text)
            ,"XD"
        )
        mBinding.viewPager.setPageTransformer(ZoomOutPageTransformer())
    }

    fun nextView(v: View){
        Navigation.findNavController(mFragment.requireView()).navigate(ViewPagerFragmentDirections.actionViewPagerFragmentToAnimationFragment())
    }

}