package com.example.capiter.module.product.domain.usecase

import com.example.capiter.module.product.data.api.RequesterProductAPI
import com.example.capiter.module.product.data.api.QueryModel
import com.example.capiter.module.product.domain.entity.Product
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductUseCase @Inject constructor(
    private val api: RequesterProductAPI,
) {

    fun get(page: Int): Single<List<Product>> {
        return api.get(QueryModel(page = page))
    }
}