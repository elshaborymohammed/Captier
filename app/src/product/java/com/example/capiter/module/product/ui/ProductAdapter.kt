package com.example.capiter.module.product.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capiter.databinding.ItemLoadingBinding
import com.example.capiter.databinding.ItemProductBinding
import com.example.capiter.module.product.domain.entity.Product

class ProductAdapter(
    private val data: MutableList<Product>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var cart: MutableMap<Int, Int> = mutableMapOf()
    private var onAddToCartClickListener: ((product: Product, position: Int) -> Unit)? = null
    private var onLoadMoreListener: ((page: Int) -> Unit)? = null

    private var loadMore = true
    private var page = 1

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            ItemViewHolder(
                ItemProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            LoadingViewHolder(
                ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(data[position], position)
        } else {
            if (loadMore) {
                onLoadMoreListener?.invoke(page + 1)
            } else {
                (holder as LoadingViewHolder).itemView.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount - 1) {
            0
        } else {
            1
        }
    }

    fun setOnAddToCartClickListener(onAddToCartClickListener: ((product: Product, position: Int) -> Unit)) {
        this.onAddToCartClickListener = onAddToCartClickListener
    }

    fun setOnLoadMoreListener(onLoadMoreListener: ((page: Int) -> Unit)? = null) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    fun update(quantity: Int, position: Int) {
        cart[position] = quantity
        notifyItemChanged(position)
    }

    fun addAll(products: List<Product>) {
        data.addAll(products)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val dataBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {

        fun bind(data: Product, position: Int) {
            dataBinding.data = data
            dataBinding.quantity.text = cart.getOrDefault(position, 0).toString()
            onAddToCartClickListener?.apply {
                dataBinding.addToCart.setOnClickListener { this.invoke(data, position) }
            }
        }
    }

    inner class LoadingViewHolder(dataBinding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(dataBinding.root)
}