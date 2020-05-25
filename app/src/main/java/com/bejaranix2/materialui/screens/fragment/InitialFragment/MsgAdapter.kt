package com.bejaranix2.materialui.screens.fragment.InitialFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bejaranix2.materialui.databinding.RecyclerItemBinding
import com.bejaranix2.materialui.databinding.RecyclerItemRightBinding
import com.bejaranix2.materialui.model.MsgModel
import java.lang.RuntimeException

class MsgAdapter(private val msgs: ArrayList<MsgModel>, private val inflater: LayoutInflater):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    class RightViewHolder(val view: RecyclerItemRightBinding) : RecyclerView.ViewHolder(view.root)
    class LeftViewHolder(val view: RecyclerItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 ->  LeftViewHolder(RecyclerItemBinding.inflate(inflater, parent, false))
            1 ->  RightViewHolder(RecyclerItemRightBinding.inflate(inflater, parent, false))
            else -> throw RuntimeException("Option not allowed")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(msgs[position].isOwner){
            false -> 0
            true -> 1
        }
    }

    override fun getItemCount(): Int = msgs.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(msgs[position].isOwner){
            false -> (holder as LeftViewHolder).view.msg = msgs[position]
            true ->  (holder as RightViewHolder).view.msg = msgs[position]
        }
    }

}
