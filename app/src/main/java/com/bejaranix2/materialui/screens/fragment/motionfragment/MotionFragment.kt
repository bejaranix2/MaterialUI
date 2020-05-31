package com.bejaranix2.materialui.screens.fragment.motionfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bejaranix2.materialui.R
import com.bejaranix2.materialui.databinding.FragmentMotionBinding
import com.bejaranix2.materialui.screens.common.binding.BindingFactory
import com.bejaranix2.materialui.screens.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_next.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MotionFragment : BaseFragment() {


    @Inject
    lateinit var bindingFactory: BindingFactory

    lateinit var manager: MotionManager

    lateinit var binding: FragmentMotionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        getFragmentComponent()?.inject(this)
        binding = bindingFactory.newInstance(FragmentMotionBinding::class.java, container)
        manager = MotionManager(binding)
        return binding.root
    }

}
