package com.example.capiter.module.order.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.capiter.R
import com.example.capiter.databinding.FragmentOrdersBinding
import com.example.capiter.module.order.domain.OrderData
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

@AndroidEntryPoint
class OrdersFragment : Fragment() {
    private val disposables = CompositeDisposable()
    private lateinit var dataBinding: FragmentOrdersBinding
    private lateinit var viewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        disposables.addAll(*subscriptions())
    }

    private fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading()
                .subscribe {
                    dataBinding.progress.visibility = if (it) View.VISIBLE else View.GONE
                },
            viewModel.get()
                .subscribe({ it ->
                    it?.apply {
                        val orderData: MutableList<OrderData> = mutableListOf()
                        it.groupBy { it.orderName }.entries
                            .forEach {
                                orderData.add(OrderData.Section(it.key!!))
                                it.value.forEach(orderData::add)
                            }
                        dataBinding.list.adapter = OrderAdapter(orderData)
                    }
                }, Throwable::printStackTrace)
        )
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}