package com.bejaranix2.materialui.screens.fragment.viewpagerfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.FragmentViewPagerBinding
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import com.bejaranix2.materialui.screens.common.binding.BindingFactory
import com.bejaranix2.materialui.screens.fragment.BaseFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ViewPagerFragment : BaseFragment() {

    @Inject
    lateinit var toolbarViewManager: ToolbarViewManager

    @Inject
    lateinit var bindingFactory: BindingFactory

    lateinit var viewPagerManager: ViewPagerManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getFragmentComponent()?.inject(this)
        val binding = bindingFactory.newInstance(FragmentViewPagerBinding::class.java, container)
        viewPagerManager = ViewPagerManager(binding, this, inflater)
        return binding.root
    }
}
