package com.apps.gmb.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var sku: String,
    var amount: Double,
    var currency: String
) : Parcelable