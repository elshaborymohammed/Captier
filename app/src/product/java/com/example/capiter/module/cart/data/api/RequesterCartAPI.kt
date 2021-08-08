package com.example.capiter.module.cart.data.api

import com.example.capiter.module.cart.domain.entity.Cart
import io.reactivex.rxjava3.core.Completable
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequesterCartAPI @Inject constructor(private val retrofit: Retrofit) {

    fun post(body: List<Cart>): Completable {
        return retrofit.create(CartAPI::class.java).post(body)
    }
}