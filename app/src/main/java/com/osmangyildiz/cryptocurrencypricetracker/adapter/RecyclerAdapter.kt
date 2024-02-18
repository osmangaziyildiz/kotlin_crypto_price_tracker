package com.osmangyildiz.cryptocurrencypricetracker.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.osmangyildiz.cryptocurrencypricetracker.databinding.RecyclerRowLayoutBinding
import com.osmangyildiz.cryptocurrencypricetracker.model.CryptocurrencyModel

class RecyclerAdapter(
    private val cryptoList: ArrayList<CryptocurrencyModel>,
    private val listener: Listener,
) :
    RecyclerView.Adapter<RecyclerAdapter.RowHolder>() {

    interface Listener {

        fun onItemClick(cryptoModel: CryptocurrencyModel)
    }

    private val colors: Array<String> = arrayOf(
        "#0f4539",
        "#fdee00",
        "#234349",
        "#8b1b13",
        "#920100",
        "#118098",
        "#213c0d",
        "#ffd5c0"
    )

    class RowHolder(private val binding: RecyclerRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            cryptocurrencyModel: CryptocurrencyModel,
            colors: Array<String>,
            position: Int,
            listener: Listener,
        ) {
            binding.textName.text = cryptocurrencyModel.name
            binding.textPrice.text  = "Price: ${cryptocurrencyModel.quote.usd.price}"
            binding.textChange24.text = "24H Change \n\n %${cryptocurrencyModel.quote.usd.percentChange24H.toString().substring(0,4)}"
            binding.root.setBackgroundColor(Color.parseColor(colors[position % 8]))
            binding.root.setOnClickListener {
                listener.onItemClick(cryptocurrencyModel)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding =
            RecyclerRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position], colors, position, listener)

    }

}