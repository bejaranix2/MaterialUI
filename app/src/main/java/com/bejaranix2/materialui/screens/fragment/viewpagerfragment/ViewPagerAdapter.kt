package com.bejaranix2.materialui.screens.fragment.viewpagerfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.bejaranix2.materialui.databinding.PageItemBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ViewPagerAdapter:RecyclerView.Adapter<ViewPagerAdapter.PageViewHolder> {

    private val mLifecycleScope: LifecycleCoroutineScope
    private val mInflater: LayoutInflater

    val list = MutableStateFlow(arrayOf<String>())

    constructor(lifecycleScope: LifecycleCoroutineScope, inflater: LayoutInflater){
        mLifecycleScope = lifecycleScope
        mInflater = inflater
        listUpdated()
    }

    class PageViewHolder(val view: PageItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        return PageViewHolder(PageItemBinding.inflate(mInflater, parent, false))
    }

    override fun getItemCount() = list.value.size

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        holder.view.txt = list.value[position]
    }

    private fun listUpdated(){
        mLifecycleScope.launch {
            list.collect {
                notifyDataSetChanged()
            }
        }
    }
}

