package com.example.capiter.module.order.data

import com.example.capiter.module.order.domain.OrderData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface OrderAPI {
    @GET("orders")
    fun get(): Single<List<OrderData.Order>>
}