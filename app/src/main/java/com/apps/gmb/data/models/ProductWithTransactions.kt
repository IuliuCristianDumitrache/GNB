package com.apps.gmb.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductWithTransactions(
    var sku: String,
    var totalAmount: Double,
    var currency: String,
    var transactions: ArrayList<Product>
): Parcelable
