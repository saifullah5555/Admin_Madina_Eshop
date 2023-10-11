package com.madinafinal.adminmadinaeshop.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madinafinal.adminmadinaeshop.databinding.DeliveryItemBinding

class DeliveryAdapter(
    private val coustomerNames: ArrayList<String>,
    private val monystatus: ArrayList<String>,
) : RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeliveryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
       holder.bind(position)
    }

    override fun getItemCount(): Int = coustomerNames.size

    inner class DeliveryViewHolder(private val binding: DeliveryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
           binding.apply {
               CoustomerName.text = coustomerNames[position]
               NotRecevied.text = monystatus[position]
               val colorMap = mapOf(
                   "Recevied" to Color.GREEN, "NotRecevied" to Color.RED, "Pending" to Color.GRAY
               )
               NotRecevied.setTextColor(colorMap[monystatus[position]]?: Color.BLACK)
               StatusColor.backgroundTintList = ColorStateList.valueOf(colorMap[monystatus[position]]?:Color.BLACK)
           }
        }

    }
}