package com.bejaranix2.materialui.screens.fragment.InitialFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment

import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.FragmentInitialBinding
import com.bejaranix2.materialui.screens.activity.ToolbarGroup
import com.bejaranix2.materialui.screens.activity.ToolbarViewManager
import com.bejaranix2.materialui.screens.common.binding.BindingFactory
import com.bejaranix2.materialui.screens.fragment.BaseFragment
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class InitialFragment : BaseFragment() {

    @Inject
    lateinit var toolbarViewManager: ToolbarViewManager

    @Inject
    lateinit var bindingFactory: BindingFactory

    lateinit var fragmentInitialBinding: FragmentInitialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getFragmentComponent()?.inject(this)
        fragmentInitialBinding = FragmentInitialBinding.inflate(inflater, container, false)
        return fragmentInitialBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarViewManager.setToolbarGroup(ToolbarGroup.EXAMPLE)
        toolbarViewManager.getNavigationListener().observe(viewLifecycleOwner, Observer {
            if(it) {
                NavHostFragment.findNavController(this)
                    .navigate(InitialFragmentDirections.actionInitialFragmentToNextFragment())
            }
        })
    }

}
