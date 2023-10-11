package com.madinafinal.adminmadinaeshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.madinafinal.adminmadinaeshop.adapter.AddItemAdapter
import com.madinafinal.adminmadinaeshop.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        val menuItemName = listOf(
            "modhu",
            "modhu",
            "modhu",
            "modhu",
            "modhu",
            "modhu",
            "modhu",
            "modhu",
            "modhu",
            "modhu"
        )
        val menuItemPrice =
            listOf("500", "500", "500", "500", "500", "500", "500", "500", "500", "500")
        val menuItemImage = listOf(
            R.drawable.addimage,
            R.drawable.menu1,
            R.drawable.menu1,
            R.drawable.menu1,
            R.drawable.menu1,
            R.drawable.menu1,
            R.drawable.menu1,
            R.drawable.menu1,
            R.drawable.menu1,
            R.drawable.menu1,
        )


        val adapter = AddItemAdapter(
            ArrayList(menuItemName),
            ArrayList(menuItemPrice),
            ArrayList(menuItemImage)
        )

        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter = adapter
    }
}