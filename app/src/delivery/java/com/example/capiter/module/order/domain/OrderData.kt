package com.example.capiter.module.order.domain

import com.google.gson.annotations.SerializedName

sealed class OrderData {

    data class Section(
        val orderName: String
    ) : OrderData()

    data class Order(
        @SerializedName("_id")
        val id: String? = null,
        @SerializedName("order-name")
        val orderName: String? = null,
        @SerializedName("product-id")
        val productId: String? = null,
        @SerializedName("product-name")
        val productName: String? = null,
        @SerializedName("product-price")
        val productPrice: Long? = null,
        @SerializedName("product-image-url")
        val productImageURL: String? = null,
        @SerializedName("product-quantity")
        val productQuantity: Long? = null
    ) : OrderData()
}