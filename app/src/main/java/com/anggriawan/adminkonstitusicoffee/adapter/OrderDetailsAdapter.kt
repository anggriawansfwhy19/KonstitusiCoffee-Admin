package com.anggriawan.adminkonstitusicoffee.adapter

import android.net.Uri
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggriawan.adminkonstitusicoffee.databinding.OrderDetailItemsBinding
import com.bumptech.glide.Glide

class OrderDetailsAdapter(
    private var context: Context,
    private var coffeeNames: ArrayList<String>,
    private var coffeeImages: ArrayList<String>,
    private var coffeeQuantitys: ArrayList<Int>,
    private var coffeePrices: ArrayList<String>
): RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val binding = OrderDetailItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = coffeeNames.size

    inner class OrderDetailsViewHolder(private val binding: OrderDetailItemsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                coffeeName.text = coffeeNames[position]
                coffeeQuantity.text = coffeeQuantitys[position].toString()
                val uriString = coffeeImages[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(coffeeImage)
                coffeePrice.text = coffeePrices[position]
            }
        }
    }

}