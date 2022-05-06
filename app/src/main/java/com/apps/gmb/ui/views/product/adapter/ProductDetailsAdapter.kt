package com.apps.gmb.ui.views.product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apps.gmb.R
import com.apps.gmb.data.models.Product
import com.apps.gmb.databinding.TransactionDetailsListItemBinding

class ProductDetailsAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var products: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_details_list_item, parent, false)

        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int = products.size

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val views = TransactionDetailsListItemBinding.bind(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = products[position]
        when (holder) {
            is ProductViewHolder -> {
                holder.views.skuTv.text = item.sku
                holder.views.currencyTv.text = item.currency
                holder.views.amountTv.text = item.amount.toString()
            }
        }
    }
}