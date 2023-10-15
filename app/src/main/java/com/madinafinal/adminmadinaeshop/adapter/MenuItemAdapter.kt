package com.madinafinal.adminmadinaeshop.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.madinafinal.adminmadinaeshop.databinding.ItemItemBinding
import com.madinafinal.adminmadinaeshop.model.AllMenu

class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference

) : RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {

    // for quantity step 1
    private val itemQuantity = IntArray(menuList.size) { 1 }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuList.size
    inner class AddItemViewHolder(private val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantity[position]
                val menuItem = menuList[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)
                foodNameView.text = menuItem.foodName
                FoodPriceView.text = menuItem.foodPrice
                // glider die fast image load kora jay
                Glide.with(context).load(uri).into(foodimageview)

                QuantityButton.text = quantity.toString()

                MinasButton.setOnClickListener {
                    minasQuantity(position)
                }
                PlusButton.setOnClickListener {
                    plusQuantity(position)
                }
                DeleteButton.setOnClickListener {
                    deleteQuantity(position)
                }
            }
        }

        private fun minasQuantity(position: Int) {
            if (itemQuantity[position]>1){
                itemQuantity[position]--
                binding.QuantityButton.text = itemQuantity[position].toString()
            }
        }

        private fun plusQuantity(position: Int) {
            if (itemQuantity[position]<10){
                itemQuantity[position]++
                binding.QuantityButton.text = itemQuantity[position].toString()
            }
        }

        private fun deleteQuantity(position: Int) { menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)
        }

    }
}