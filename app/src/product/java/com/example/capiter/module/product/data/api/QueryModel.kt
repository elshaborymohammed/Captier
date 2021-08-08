package com.example.capiter.module.product.data.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class QueryModel(
    @SerializedName("page")
    val page: Int
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}