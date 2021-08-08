package com.example.capiter.module.order.data

import com.example.capiter.module.order.domain.OrderData
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject

class RequesterOrderAPI @Inject constructor(private val retrofit: Retrofit) {
    @GET("/orders")
    fun get(): Single<List<OrderData.Order>> {
        return retrofit.create(OrderAPI::class.java).get()
    }
}