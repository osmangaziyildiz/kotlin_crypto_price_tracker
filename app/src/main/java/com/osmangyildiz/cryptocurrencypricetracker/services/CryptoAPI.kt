package com.osmangyildiz.cryptocurrencypricetracker.services

import com.osmangyildiz.cryptocurrencypricetracker.model.CryptocurrencyResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CryptoAPI {

    @GET("v1/cryptocurrency/listings/latest")
    fun getCryptocurrencies(
        @Query("start") start: String,
        @Query("limit") limit: String,
        @Query("convert") convert: String,
        @Header("X-CMC_PRO_API_KEY") apiKey: String,
    ): Observable<CryptocurrencyResponse>

}