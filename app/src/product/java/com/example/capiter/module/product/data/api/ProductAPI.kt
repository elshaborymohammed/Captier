package com.example.capiter.module.product.data.api

import com.example.capiter.module.product.domain.entity.Product
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductAPI {
    @GET("products")
    fun get(@Query("q") query: QueryModel): Single<List<Product>>
}