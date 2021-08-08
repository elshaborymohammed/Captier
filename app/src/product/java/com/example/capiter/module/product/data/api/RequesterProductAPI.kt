package com.example.capiter.module.product.data.api

import com.example.capiter.module.product.domain.entity.Product
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequesterProductAPI @Inject constructor(private val retrofit: Retrofit) {

    fun get(query: QueryModel): Single<List<Product>> {
        println(query)
        return retrofit.create(ProductAPI::class.java).get(query)
    }
}