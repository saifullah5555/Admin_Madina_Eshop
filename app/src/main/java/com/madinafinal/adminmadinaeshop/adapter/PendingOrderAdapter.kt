package com.madinafinal.adminmadinaeshop.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.madinafinal.adminmadinaeshop.databinding.PendingOrderItemBinding

class PendingOrderAdapter(
    private val context: Context,
    private val CoustomerNames: MutableList<String>,
    private val Quantityss: MutableList<String>,
    private val FoodImagess: MutableList<String>,
    private val itemClicked: OnItemClicked
) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {
    interface OnItemClicked{
        fun onItemClickListener(position:Int)
        fun onItemAcceptClickListener(position:Int)
        fun onItemDispatchClickListener(position:Int)

    }


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
                var uriString = FoodImagess[position]
              var uri = Uri.parse(uriString)

               Glide.with(context).load(uri).into(pandingOrderFoodItemImage)

                OrderAcceptButton.apply {
                     if (!isAccepted) {
                        "Accept"
                    } else {
                        "Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted) {
                            text = "Dispatch"
                            isAccepted = true
                            showToast("Order is Accepted")
                            itemClicked.onItemAcceptClickListener(position)
                        } else {
                            CoustomerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order Is Dispatch")
                            itemClicked.onItemDispatchClickListener(position)

                        }
                    }
                }
                itemView.setOnClickListener {
                    itemClicked.onItemClickListener(position)
                }
            }


        }
       private fun showToast(message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}