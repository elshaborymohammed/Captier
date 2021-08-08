package com.example.capiter.module.product.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.capiter.R
import com.example.capiter.databinding.FragmentProductBinding
import com.example.capiter.module.cart.ui.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

@AndroidEntryPoint
class ProductFragment : Fragment() {
    private val disposables = CompositeDisposable()
    private lateinit var dataBinding: FragmentProductBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        disposables.addAll(*subscriptions())

        dataBinding.toolbar.setOnMenuItemClickListener {
            findNavController().navigate(R.id.action_productFragment_to_cartFragment)
            true
        }
    }

    private fun subscriptions(): Array<Disposable> {
        return arrayOf(
            productViewModel.loading()
                .subscribe {
                    dataBinding.progress.visibility = if (it) View.VISIBLE else View.GONE
                },
            productViewModel.get(page = 1)
                .subscribe({ it ->
                    it?.apply {
                        dataBinding.list.adapter = ProductAdapter(this.toMutableList())
                            .apply {
                                setOnAddToCartClickListener { product, position ->
                                    val quantity = cartViewModel.add(product)
                                    update(quantity, position)
                                }

                                setOnLoadMoreListener { page ->
                                    disposables.add(
                                        productViewModel.get(page = page).subscribe({
                                            addAll(it.toMutableList())
                                        }, Throwable::printStackTrace)
                                    )
                                }
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