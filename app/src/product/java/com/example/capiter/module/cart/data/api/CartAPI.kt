package com.example.capiter.module.cart.data.api

import com.example.capiter.module.cart.domain.entity.Cart
import io.reactivex.rxjava3.core.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface CartAPI {
    @POST("orders")
    fun post(@Body body: List<Cart>): Completable
}