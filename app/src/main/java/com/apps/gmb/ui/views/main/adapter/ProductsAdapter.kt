package com.apps.gmb.ui.views.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apps.gmb.R
import com.apps.gmb.data.models.ProductWithTransactions
import com.apps.gmb.databinding.ProductListItemBinding

class ProductsAdapter(private val listener: OnProductsInteractionListener?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var products: List<ProductWithTransactions> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_list_item, parent, false)

        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int = products.size

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val views = ProductListItemBinding.bind(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = products[position]
        when (holder) {
            is ProductViewHolder -> {
                holder.views.productTv.text = item.sku

                holder.views.root.setOnClickListener {
                    listener?.onProductItemClicked(item)
                }
            }
        }
    }

    interface OnProductsInteractionListener {
        fun onProductItemClicked(productWithTransactions: ProductWithTransactions)
    }
}