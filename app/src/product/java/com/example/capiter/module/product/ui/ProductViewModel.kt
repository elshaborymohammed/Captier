package com.example.capiter.module.product.ui

import androidx.lifecycle.ViewModel
import com.example.capiter.module.product.domain.entity.Product
import com.example.capiter.module.product.domain.usecase.ProductUseCase
import com.jakewharton.rxrelay3.Relay
import com.jakewharton.rxrelay3.ReplayRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val useCase: ProductUseCase,
) : ViewModel() {

    private val loading = ReplayRelay.create<Boolean>()

    fun loading(): Relay<Boolean> {
        return loading
    }

    fun get(page: Int): Single<List<Product>> {
        return useCase.get(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loading.accept(true)
            }
            .doOnSuccess {
                loading.accept(false)
            }
            .doOnError {
                loading.accept(false)
            }
    }

}