package com.example.capiter.module.order.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capiter.databinding.ItemOrderBinding
import com.example.capiter.databinding.ItemSectionBinding
import com.example.capiter.module.order.domain.OrderData

class OrderAdapter(private val data: List<OrderData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is OrderData.Section -> 0
            is OrderData.Order -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            SectionViewHolder(
                ItemSectionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            OrderViewHolder(
                ItemOrderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SectionViewHolder) {
            holder.bind(data[position] as OrderData.Section)
        } else if (holder is OrderViewHolder) {
            holder.bind(data[position] as OrderData.Order)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class SectionViewHolder(private val dataBinding: ItemSectionBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        fun bind(data: OrderData.Section) {
            dataBinding.data = data
        }
    }

    inner class OrderViewHolder(private val dataBinding: ItemOrderBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        fun bind(data: OrderData.Order) {
            dataBinding.data = data
        }
    }
}