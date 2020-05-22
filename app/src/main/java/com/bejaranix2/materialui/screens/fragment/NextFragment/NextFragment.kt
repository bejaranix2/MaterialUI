package com.bejaranix2.materialui.screens.fragment.NextFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment

import com.bejaranix2.materialui.databinding.FragmentNextBinding
import com.bejaranix2.materialui.screens.activity.ToolbarGroup
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import com.bejaranix2.materialui.screens.common.binding.BindingFactory
import com.bejaranix2.materialui.screens.fragment.BaseFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class NextFragment : BaseFragment() {

    @Inject
    lateinit var toolbarViewManager: ToolbarViewManager

    @Inject
    lateinit var bindingFactory: BindingFactory

    lateinit var fragmentNextBinding: FragmentNextBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getFragmentComponent()?.inject(this)
        fragmentNextBinding = bindingFactory.newInstance(FragmentNextBinding::class.java, container)
        return fragmentNextBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarViewManager.setToolbarGroup(ToolbarGroup.ANOTHER_EXAMPLE)
        toolbarViewManager.getNavigationListener().observe(viewLifecycleOwner, Observer {
            if(it) {
                NavHostFragment.findNavController(this).navigateUp()
            }
        })
    }


}
