package com.osmangyildiz.cryptocurrencypricetracker.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.osmangyildiz.cryptocurrencypricetracker.adapter.RecyclerAdapter
import com.osmangyildiz.cryptocurrencypricetracker.databinding.ActivityMainBinding
import com.osmangyildiz.cryptocurrencypricetracker.model.CryptocurrencyModel
import com.osmangyildiz.cryptocurrencypricetracker.model.CryptocurrencyResponse
import com.osmangyildiz.cryptocurrencypricetracker.services.CryptoAPI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerAdapter.Listener {

    private val BASE_URL = "https://pro-api.coinmarketcap.com"
    private var cryptoModels: ArrayList<CryptocurrencyModel>? = null
    private var recyclerAdapter: RecyclerAdapter? = null
    private lateinit var binding: ActivityMainBinding
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        compositeDisposable = CompositeDisposable()
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerView.layoutManager = layoutManager
        loadData()
    }

    private fun loadData() {

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build().create(CryptoAPI::class.java)

        val start = "1"
        val limit = "100"
        val convert = "USD"
        val apiKey = "************************************"

        compositeDisposable?.add(
            retrofit.getCryptocurrencies(start, limit, convert, apiKey).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResponse)
        )
    }

    private fun handleResponse(cryptocurrencyResponse: CryptocurrencyResponse) {
        cryptoModels = ArrayList(cryptocurrencyResponse.data)
        cryptoModels?.let {
            recyclerAdapter = RecyclerAdapter(cryptoModels!!, this)
            binding.recyclerView.adapter = recyclerAdapter
        }
    }

    override fun onItemClick(cryptoModel: CryptocurrencyModel) {
        Toast.makeText(this, "Clicked : ${cryptoModel.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}