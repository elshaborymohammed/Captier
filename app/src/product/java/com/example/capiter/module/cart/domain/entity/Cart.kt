package com.example.capiter.module.cart.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Cart(
    @SerializedName("order-name")
    var orderName: String?,

    @PrimaryKey
    @ColumnInfo(name = "product-id")
    @SerializedName("product-id")
    var productId: String,

    @ColumnInfo(name = "product-name")
    @SerializedName("product-name")
    var productName: String?,

    @ColumnInfo(name = "product-price")
    @SerializedName("product-price")
    var productPrice: Float?,

    @ColumnInfo(name = "product-image-url")
    @SerializedName("product-image-url")
    var productImageURL: String?,

    @ColumnInfo(name = "product-quantity")
    @SerializedName("product-quantity")
    var productQuantity: Int?
)