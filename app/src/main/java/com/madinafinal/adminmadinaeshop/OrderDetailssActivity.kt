package com.madinafinal.adminmadinaeshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.madinafinal.adminmadinaeshop.adapter.OrderDetailsAdapter
import com.madinafinal.adminmadinaeshop.databinding.ActivityOrderDetailssBinding
import com.madinafinal.adminmadinaeshop.model.OrderDetails

class OrderDetailssActivity : AppCompatActivity() {
    private val binding: ActivityOrderDetailssBinding by lazy {
        ActivityOrderDetailssBinding.inflate(layoutInflater)
    }
   private lateinit var adapter: OrderDetailsAdapter
    private var userNamess: String? = null
    private var address: String? = null
    private var phone: String? = null
    private var totalprice: String? = null

    private var foodNames: ArrayList<String> = arrayListOf()
    private var foodImages: ArrayList<String> = arrayListOf()
    private var foodQuantity: ArrayList<Int> = arrayListOf()
    private var foodPrice: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.payoutBackBtn.setOnClickListener {
            finish()
        }

        getDataFromIntent()

    }

    private fun getDataFromIntent() {
        val reservedOrderDetails = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        reservedOrderDetails.let { orderDetails ->
            userNamess = reservedOrderDetails.userName
            foodImages = reservedOrderDetails.foodImages as ArrayList<String>
            foodQuantity = reservedOrderDetails.foodQuantities as ArrayList<Int>
            foodPrice = reservedOrderDetails.foodPrices as ArrayList<String>
            address = reservedOrderDetails.address
            phone = reservedOrderDetails.phoneNumber
            foodPrice = reservedOrderDetails.foodPrices as ArrayList<String>
            totalprice = reservedOrderDetails.totalPrices

            setUserDetails()
            setAdapter()
        }


    }


    private fun setUserDetails() {
        binding.orderWonarName.text = userNamess
        binding.wonerPhone.text = phone
        binding.wonerAddress.text = address
        binding.wonerTotalAmount.text = totalprice
    }

    private fun setAdapter() {

        adapter = OrderDetailsAdapter(this, foodNames, foodImages, foodQuantity, foodPrice)
        binding.orderDetailsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.orderDetailsRecyclerView.adapter = adapter
    }

}