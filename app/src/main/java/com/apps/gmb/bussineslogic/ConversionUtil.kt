package com.apps.gmb.bussineslogic

import com.apps.gmb.data.models.Conversion

object ConversionUtil {
    private lateinit var visited: HashSet<String>
    private lateinit var exchangeRates: ArrayList<Conversion>

    private const val DEFAULT_EXCHANGE_RATE = 1.0
    private const val NO_EXCHANGE_RATE = 0.0

    fun setRates(exchangeRates: ArrayList<Conversion>) {
        this.exchangeRates = exchangeRates
    }

    fun getExchangeRate(from: String, to: String): Double {
        return if (from != to) {
            visited = HashSet()
            findExchangeRate(from, to, DEFAULT_EXCHANGE_RATE)
        } else {
            DEFAULT_EXCHANGE_RATE
        }
    }

    private fun findExchangeRate(from: String, to: String, value: Double): Double {
        val resultRate = findExchangeRateBetween(from, to)
        if (resultRate != NO_EXCHANGE_RATE) {
            return value * resultRate
        }
        visited.add(from)

        exchangeRates.forEach { exchange ->
            if (exchange.from == from && !visited.contains(to)) {
                val result = findExchangeRate(exchange.to, to, value * exchange.rate)
                if (result != NO_EXCHANGE_RATE) {
                    return result
                }
            }
        }
        return DEFAULT_EXCHANGE_RATE
    }

    private fun findExchangeRateBetween(from: String, to: String): Double {
        exchangeRates.forEach { exchange ->
            if (exchange.from == from && exchange.to == to) {
                return exchange.rate
            }
        }
        return NO_EXCHANGE_RATE
    }
}