package com.example.capiter.module.cart.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capiter.databinding.ItemCartBinding
import com.example.capiter.module.cart.domain.entity.Cart
import kotlin.streams.toList

class CartAdapter(
    private val data: MutableList<Cart>,
    private val onItemClickListener: ((cart: Cart) -> Unit)? = null
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun delete(position: Int) {
        data.removeAt(position)
        notifyDataSetChanged()
    }

    fun getOrder(order: String): List<Cart> {
        return data.stream().peek { it.orderName = order }.toList()
    }

    inner class ViewHolder(private val dataBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        fun bind(data: Cart, position: Int) {
            dataBinding.data = data
            dataBinding.delete.setOnClickListener {
                onItemClickListener?.apply {
                    invoke(data)
                    delete(position)
                }
            }
        }
    }
}