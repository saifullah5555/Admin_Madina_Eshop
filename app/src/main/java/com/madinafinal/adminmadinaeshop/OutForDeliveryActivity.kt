package com.madinafinal.adminmadinaeshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.madinafinal.adminmadinaeshop.adapter.AddItemAdapter
import com.madinafinal.adminmadinaeshop.adapter.DeliveryAdapter
import com.madinafinal.adminmadinaeshop.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding: ActivityOutForDeliveryBinding by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val coustomerNamee = arrayListOf("Omor Faruk", "Moin Saheb", "Mufty Saheb", "Yasin Saheb", "Ami Saheb")
        val monyRecevd = arrayListOf("Recevied","NotRecevied","Pending","Recevied","NotRecevied")

        val adapter = DeliveryAdapter(coustomerNamee, monyRecevd)

        binding.deliveryRecyclerView.adapter = adapter
        binding.deliveryRecyclerView.layoutManager = LinearLayoutManager(this)

    }
}