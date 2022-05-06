package com.apps.gmb.ui.views.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apps.gmb.GmbApplication
import com.apps.gmb.R
import com.apps.gmb.bussineslogic.ConversionUtil
import com.apps.gmb.data.models.Conversion
import com.apps.gmb.data.models.Product
import com.apps.gmb.data.models.ProductWithTransactions
import com.apps.gmb.data.networking.ApiFactory
import kotlinx.coroutines.*
import retrofit2.Response
import kotlin.math.round

class ProductsViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val apiService = ApiFactory.getClient()
    val productsLiveData: MutableLiveData<ArrayList<ProductWithTransactions>> = MutableLiveData()
    val conversionReadyLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val productsHashMap = mutableMapOf<String, ProductWithTransactions>()

    var serverProductList: ArrayList<Product> = ArrayList()
    var serverConversionList: ArrayList<Conversion> = ArrayList()

    fun getConversionList() {
        ioScope.launch {
            val result: Response<ArrayList<Conversion>> = apiService.getConversions()
            if (result.isSuccessful) {
                serverConversionList = result.body() ?: ArrayList()
            }

            ConversionUtil.setRates(serverConversionList)
            conversionReadyLiveData.postValue(true)
        }
    }

    fun loadProducts() {
        ioScope.launch {
            val result: Response<ArrayList<Product>> = apiService.getProducts()
            if (result.isSuccessful) {
                serverProductList = result.body() ?: ArrayList()
            }

            val products: ArrayList<ProductWithTransactions> = ArrayList()

            addAll(serverProductList)

            productsHashMap.forEach {
                val newProductDetails = ProductWithTransactions(it.key, it.value.totalAmount,  GmbApplication.getAppContext().getString(R.string.euro_label), it.value.transactions)
                products.add(newProductDetails)
            }
            productsLiveData.postValue(products)
        }
    }

    private fun addAll(serverProductList: ArrayList<Product>) {
        serverProductList.forEach { product ->
            add(product)
        }
    }

    private fun add(product: Product) {
        if (product.currency != GmbApplication.getAppContext().getString(R.string.euro_label)) {
            product.amount = convertToEuro(product.amount, product.currency)
        }
        if (productsHashMap.containsKey(product.sku)) {
            val productNewSum = mergeAmount(productsHashMap[product.sku]!!.totalAmount, product)
            productsHashMap[product.sku]!!.totalAmount = productNewSum
            productsHashMap[product.sku]!!.transactions.add(product)
        } else {
            val newProductWithTransaction = ProductWithTransactions(product.sku, product.amount, product.currency, arrayListOf(product))
            productsHashMap[product.sku] = newProductWithTransaction
        }
    }

    private fun convertToEuro(amount: Double, currency: String): Double {
        return round((ConversionUtil.getExchangeRate(currency, GmbApplication.getAppContext().getString(R.string.euro_label)) * amount) * 100) / 100
    }

    private fun mergeAmount(sum: Double, product: Product): Double {
        return round((sum + product.amount) * 100) / 100
    }
}