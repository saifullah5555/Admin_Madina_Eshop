
package com.madinafinal.adminmadinaeshop.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.madinafinal.adminmadinaeshop.R
import com.madinafinal.adminmadinaeshop.databinding.RowImagesPicedBinding
import com.madinafinal.adminmadinaeshop.model.ModelImagePicked55
import java.lang.Exception



// this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root
// this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root
// this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root


class AdapterImagePiked55(
    private val context: Context,
    private val imagesPikedArrayList: ArrayList<ModelImagePicked55>,


    ) : Adapter<AdapterImagePiked55.HolderImagePiked>() {
    private lateinit var binding: RowImagesPicedBinding

    private companion object {
        private const val TAG = "IMAGES_TAG"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderImagePiked {
        binding = RowImagesPicedBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderImagePiked(binding.root)
    }

    override fun onBindViewHolder(holder: HolderImagePiked, position: Int) {
val model = imagesPikedArrayList[position]
        val imageUri = model.imageUri
        Log.d(TAG, "onBindViewHolder: imageUri $imageUri")

        try {

            Glide.with(context)
                .load(imageUri)
                .placeholder(R.drawable.dollar)
                .into(holder.imageIv)

        } catch (e:Exception){
            Log.d(TAG, "onBindViewHolder: ",e)
        }


        holder.closeBtn.setOnClickListener {

            imagesPikedArrayList.remove(model)

            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return imagesPikedArrayList.size
    }


    inner class HolderImagePiked(itemView: View) : ViewHolder(itemView) {


        var imageIv = binding.imageIV
        var closeBtn = binding.closeBtn


    }

}