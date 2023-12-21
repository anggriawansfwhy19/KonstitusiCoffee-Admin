package com.anggriawan.adminkonstitusicoffee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggriawan.adminkonstitusicoffee.adapter.OrderDetailsAdapter
import com.anggriawan.adminkonstitusicoffee.databinding.ActivityOrderDetailsBinding
import com.anggriawan.adminkonstitusicoffee.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding : ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }
    private var userName : String? = null
    private var address : String? = null
    private var phoneNumber : String? = null
    private var totalPrice : String? = null
    private var coffeeNames : ArrayList<String> = arrayListOf()
    private var coffeeImages : ArrayList<String> = arrayListOf()
    private var coffeeQuantity : ArrayList<Int> = arrayListOf()
    private var coffeePrices : ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }
        getDataFromIntent()
    }

    private fun getDataFromIntent() {

        val receivedOrderDetails = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        receivedOrderDetails?.let { orderDetails ->

                userName = receivedOrderDetails.userName
                coffeeNames = receivedOrderDetails.coffeeNames as ArrayList<String>
                coffeeImages = receivedOrderDetails.coffeeImages as ArrayList<String>
                coffeeQuantity = receivedOrderDetails.coffeeQuantities as ArrayList<Int>
                address = receivedOrderDetails.address
                phoneNumber = receivedOrderDetails.phoneNumber
                coffeePrices = receivedOrderDetails.coffeePrices as ArrayList<String>
                totalPrice = receivedOrderDetails.totalPrice

                setUserDetails()

                setAdapter()
        }
    }

    private fun setUserDetails() {
        binding.name.text = userName
        binding.address.text = address
        binding.phone.text = phoneNumber
        binding.totalPay.text = totalPrice
    }
    private fun setAdapter() {
        binding.orderDetailsRV.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDetailsAdapter(this, coffeeNames, coffeeImages, coffeeQuantity, coffeePrices)
        binding.orderDetailsRV.adapter = adapter
    }
}