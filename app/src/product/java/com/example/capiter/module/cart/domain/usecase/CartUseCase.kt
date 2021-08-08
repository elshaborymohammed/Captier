package com.example.capiter.module.cart.domain.usecase

import com.example.capiter.module.cart.data.api.RequesterCartAPI
import com.example.capiter.module.cart.data.db.CartDAO
import com.example.capiter.module.cart.domain.entity.Cart
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartUseCase @Inject constructor(
    private val dao: CartDAO,
    private val api: RequesterCartAPI,
) {

    fun get(): Single<List<Cart>> {
        return dao.find()
    }

    fun add(cart: Cart): Int {
        if (dao.exists(cart.productId)) {
            dao.findOne(cart.productId).productQuantity?.also {
                cart.productQuantity = it + 1
            }
            dao.update(cart)
        } else {
            dao.insert(cart)
        }

        return cart.productQuantity!!
    }

    fun post(body: List<Cart>): @NonNull Completable {
        return api.post(body)
    }

    fun delete(cart: Cart) {
        dao.delete(cart)
    }

    fun delete() {
        dao.delete()
    }
}