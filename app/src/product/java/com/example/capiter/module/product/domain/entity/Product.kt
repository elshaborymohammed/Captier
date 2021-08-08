package com.example.capiter.module.product.domain.entity

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Float,
    @SerializedName("image-url")
    val imageURL: String
)