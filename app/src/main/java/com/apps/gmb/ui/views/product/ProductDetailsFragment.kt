package com.apps.gmb.ui.views.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.gmb.R
import com.apps.gmb.bussineslogic.AlertDialogUtil
import com.apps.gmb.bussineslogic.viewBinding
import com.apps.gmb.data.models.Product
import com.apps.gmb.data.models.ProductWithTransactions
import com.apps.gmb.databinding.ProductDetailsFragmentBinding
import com.apps.gmb.ui.views.main.ProductsFragment.Companion.ARG_PRODUCT
import com.apps.gmb.ui.views.product.adapter.ProductDetailsAdapter

class ProductDetailsFragment : Fragment() {

    private val views by viewBinding(ProductDetailsFragmentBinding::bind)

    private var productWithTransactions: ProductWithTransactions? = null
    private var productDetailsAdapter: ProductDetailsAdapter = ProductDetailsAdapter()

    companion object {
        @JvmStatic
        fun newInstance(product: Product) =
            ProductDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PRODUCT, productWithTransactions)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_details_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productWithTransactions = it.getParcelable(ARG_PRODUCT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        initRecyclerView()

        productDetailsAdapter.products = productWithTransactions?.transactions ?: arrayListOf()
    }

    private fun initRecyclerView() {
        views.transactions.layoutManager = LinearLayoutManager(context)
        productDetailsAdapter = ProductDetailsAdapter()
        views.transactions.adapter = productDetailsAdapter
    }

    private fun initListeners() {
        views.closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        views.totalBtn.setOnClickListener {
            context?.let { context ->
                productWithTransactions?.let { product ->
                    AlertDialogUtil.createCustomAlertDialog(
                        context,
                        product.sku,
                        product.totalAmount.toString() + (product.currency),
                        positiveButton = Pair(getString(R.string.lbl_ok), { dialog, _ ->
                            dialog.dismiss()
                        }),
                        negativeButton = null
                    ).show()
                }
            }
        }
    }

    private fun initUi() {
        views.titleTv.text = getString(R.string.details_for_product, productWithTransactions?.sku)
    }
}