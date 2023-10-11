package com.madinafinal.adminmadinaeshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madinafinal.adminmadinaeshop.databinding.ItemItemBinding

class AddItemAdapter(
    private val MenuItemName: ArrayList<String>,
    private val MenuItemPrice: ArrayList<String>,
    private val MenuItemImage: ArrayList<Int>,
) : RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {

    // for quantity step 1
    private val itemQuantity = IntArray(MenuItemName.size) { 1 }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = MenuItemName.size
    inner class AddItemViewHolder(private val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantity[position]
                foodNameView.text = MenuItemName[position]
                FoodPriceView.text = MenuItemPrice[position]
                foodimageview.setImageResource(MenuItemImage[position])

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

        private fun deleteQuantity(position: Int) {
            MenuItemName.removeAt(position)
            MenuItemPrice.removeAt(position)
            MenuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, MenuItemName.size)
        }

    }
}