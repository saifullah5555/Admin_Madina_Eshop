package com.madinafinal.adminmadinaeshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.madinafinal.adminmadinaeshop.adapter.PendingOrderAdapter
import com.madinafinal.adminmadinaeshop.databinding.ActivityPendingOrderBinding

class PendingOrderActivity : AppCompatActivity() {
    private val binding: ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val pendingordercoustomerNamee = arrayListOf("Omor Faruk", "Moin Saheb", "Mufty Saheb", "Yasin Saheb", "Ami Saheb")
        val pendingorderquantity= arrayListOf("5","3","9","4","7")
        val Pendingorderimage = arrayOf(R.drawable.banner2,
            R.drawable.banner2,
            R.drawable.banner1,
            R.drawable.banner3,
            R.drawable.banner1,
            R.drawable.banner3,
            )

        val adapter = PendingOrderAdapter(pendingordercoustomerNamee, pendingorderquantity,Pendingorderimage,this)

        binding.PendingOrderRecycleView.adapter = adapter
        binding.PendingOrderRecycleView.layoutManager = LinearLayoutManager(this)
    }
}