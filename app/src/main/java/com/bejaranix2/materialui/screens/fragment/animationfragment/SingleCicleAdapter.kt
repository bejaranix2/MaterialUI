package com.bejaranix2.materialui.screens.fragment.animationfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bejaranix2.materialui.databinding.CardItemBinding

class SingleCicleAdapter(val data: ArrayList<SingleData>, val inflater: LayoutInflater): CircleAdapter<CircleAdapter.CircleViewHolder>() {


    fun addElement(singleData: SingleData){
        data.add(singleData)
        notifyItemInsertedAtEnd()
    }

    class ViewHolder(val v:CardItemBinding):CircleViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardItemBinding.inflate(inflater, parent, false) )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CircleViewHolder, position: Int) {
        ( holder as ViewHolder).v.data = data[position]
    }

}

data class SingleData(val title:String, val text:String)