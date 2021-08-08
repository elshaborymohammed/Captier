package com.example.capiter.module.cart.ui

import androidx.lifecycle.ViewModel
import com.example.capiter.module.cart.domain.entity.Cart
import com.example.capiter.module.cart.domain.usecase.CartUseCase
import com.example.capiter.module.product.domain.entity.Product
import com.jakewharton.rxrelay3.Relay
import com.jakewharton.rxrelay3.ReplayRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val useCase: CartUseCase,
) : ViewModel() {

    private val loading = ReplayRelay.create<Boolean>()

    fun loading(): Relay<Boolean> {
        return loading
    }

    fun get(): Single<List<Cart>> {
        return useCase.get()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.accept(true) }
            .doOnSuccess { loading.accept(false) }
            .doOnError { loading.accept(false) }
    }

    fun add(product: Product): Int {
        return useCase.add(
            Cart(
                orderName = "",
                productId = product.id,
                productName = product.name,
                productPrice = product.price,
                productImageURL = product.imageURL,
                productQuantity = 1,
            )
        )
    }

    fun post(body: List<Cart>): @NonNull Completable {
        return useCase.post(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.accept(true) }
            .doOnComplete { loading.accept(false) }
            .doOnError { loading.accept(false) }
    }

    fun delete(cart: Cart) {
        useCase.delete(cart)
    }

    fun delete() {
        useCase.delete()
    }

}