package com.example.capiter.module.order.ui

import androidx.lifecycle.ViewModel
import com.example.capiter.module.order.data.RequesterOrderAPI
import com.example.capiter.module.order.domain.OrderData
import com.jakewharton.rxrelay3.Relay
import com.jakewharton.rxrelay3.ReplayRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val api: RequesterOrderAPI,
) : ViewModel() {

    private val loading = ReplayRelay.create<Boolean>()

    fun loading(): Relay<Boolean> {
        return loading
    }

    fun get(): Single<List<OrderData.Order>> {
        return api.get()
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