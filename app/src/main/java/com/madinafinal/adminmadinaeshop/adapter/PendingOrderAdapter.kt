package com.madinafinal.adminmadinaeshop.adapter

import android.content.Context
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.madinafinal.adminmadinaeshop.databinding.PendingOrderItemBinding

class PendingOrderAdapter(
    private val CoustomerNames: ArrayList<String>,
    private val Quantityss: ArrayList<String>,
    private val FoodImagess: Array<Int>,
    private val context: Context
) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding =
            PendingOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = CoustomerNames.size

    inner class PendingOrderViewHolder(private val binding: PendingOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                PendingOrderCoustomerName.text = CoustomerNames[position]
                PendingQuantity.text = Quantityss[position]
                pandingOrderFoodItemImage.setImageResource(FoodImagess[position])

                OrderAcceptButton.apply {
                    if (!isAccepted) {
                        text = "Accept"
                    } else {
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Dispatch"
                            isAccepted = true
                            showToast("Order is Accepted")
                        } else {
                            CoustomerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order Is Dispatch")

                        }
                    }
                }
            }


        }
       private fun showToast(message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}