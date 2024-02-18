package com.osmangyildiz.cryptocurrencypricetracker.model

import com.google.gson.annotations.SerializedName

data class CryptocurrencyResponse(
    @SerializedName("data") val data: List<CryptocurrencyModel>,
)

data class CryptocurrencyModel(
    @SerializedName("name") val name: String,
    @SerializedName("quote") val quote: Quote,
)

data class Quote(
    @SerializedName("USD") val usd: QuoteDetails,
)

data class QuoteDetails(
    @SerializedName("price") val price: Double,
    @SerializedName("percent_change_24h") val percentChange24H: Double,
)