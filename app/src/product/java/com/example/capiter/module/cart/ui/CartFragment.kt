package com.example.capiter.module.cart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.capiter.R
import com.example.capiter.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

@AndroidEntryPoint
class CartFragment : Fragment() {
    private val disposables = CompositeDisposable()
    private lateinit var dataBinding: FragmentCartBinding
    private lateinit var viewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        disposables.addAll(*subscriptions())
        dataBinding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            placedOrder.setOnClickListener {
                viewModel.post(cartAdapter.getOrder(orderName.text.toString()))
                    .subscribe({
                        viewModel.delete()
                        findNavController().navigateUp()
                        Toast.makeText(context, "Your Order has been placed", Toast.LENGTH_LONG)
                            .show()
                    }, Throwable::printStackTrace)
            }
        }
    }

    private fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.loading()
                .subscribe {
                    dataBinding.progress.visibility = if (it) View.VISIBLE else View.GONE
                },
            viewModel.get()
                .subscribe({
                    it?.apply {
                        dataBinding.list.adapter =
                            CartAdapter(it.toMutableList(), viewModel::delete).apply {
                                cartAdapter = this
                            }
                    }
                }, Throwable::printStackTrace)
        )
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}