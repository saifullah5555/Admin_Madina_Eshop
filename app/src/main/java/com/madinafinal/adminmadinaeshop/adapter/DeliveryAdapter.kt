package com.madinafinal.adminmadinaeshop.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madinafinal.adminmadinaeshop.databinding.DeliveryItemBinding

class DeliveryAdapter(
    private val coustomerNames: MutableList<String>,
    private val monystatus: MutableList<Boolean>
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
               if (monystatus[position] == true) {
                   NotRecevied.text = "Received"
               }else{
                   NotRecevied.text = "NotReceived"
               }

               val colorMap = mapOf(
                   true to Color.GREEN,false  to Color.RED,
               )
               NotRecevied.setTextColor(colorMap[monystatus[position]]?: Color.BLACK)
               StatusColor.backgroundTintList = ColorStateList.valueOf(colorMap[monystatus[position]]?:Color.BLACK)
           }
        }

    }
}