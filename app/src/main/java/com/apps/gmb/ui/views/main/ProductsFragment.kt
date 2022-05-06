package com.apps.gmb.ui.views.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.gmb.R
import com.apps.gmb.bussineslogic.viewBinding
import com.apps.gmb.data.models.ProductWithTransactions
import com.apps.gmb.databinding.ProductsFragmentBinding
import com.apps.gmb.ui.views.main.adapter.ProductsAdapter
import com.apps.gmb.ui.views.main.viewmodel.ProductsViewModel

class ProductsFragment : Fragment(), ProductsAdapter.OnProductsInteractionListener {

    private val views by viewBinding(ProductsFragmentBinding::bind)

    private val productsViewModel by lazy {
        ViewModelProvider(this).get(ProductsViewModel::class.java)
    }

    private var productsAdapter: ProductsAdapter = ProductsAdapter(this)

    companion object {
        const val ARG_PRODUCT = "product"

        @JvmStatic
        fun newInstance() =
            ProductsFragment().apply {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productsViewModel.conversionReadyLiveData.observe(this) { conversionReady ->
            if (conversionReady) {
                productsViewModel.loadProducts()
            }
        }

        productsViewModel.productsLiveData.observe(this) { products ->
            productsAdapter.products = products
            views.recyclerView.adapter = productsAdapter
            views.recyclerView.invalidate()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        views.recyclerView.layoutManager = LinearLayoutManager(context)
        productsAdapter = ProductsAdapter(this)
        views.recyclerView.adapter = productsAdapter
    }


    override fun onResume() {
        super.onResume()
        productsViewModel.getConversionList()
    }

    override fun onProductItemClicked(productWithTransactions: ProductWithTransactions) {
        findNavController()
            .navigate(
                R.id.action_productsFragment_to_productDetailsFragment,
                bundleOf(
                    ARG_PRODUCT to productWithTransactions
                )
            )
    }
}