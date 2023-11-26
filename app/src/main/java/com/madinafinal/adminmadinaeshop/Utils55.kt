package com.madinafinal.adminmadinaeshop

import android.content.Context
import android.text.format.DateFormat
import android.widget.Toast
import java.util.Calendar
import java.util.Locale


// this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root
// this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root
// this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root
// this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root
// this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root


/* A class that will contain static functions , constants, variables, that will be used in whole application*/
object Utils55 {

//constants to define possible Ads status. When ad is published the Ad status will be set AVAILABLE in
// firebase db. so user can mark as SOLD later when it is sold

    const val AD_STATUS_AVAILABLE = "AVAILABLE"
    const val AD_STATUS_SOLD = "SOLD"


    //Categories array of the ads
    val categorys = arrayOf(
        "মধু","মিসওয়াক","তাসবিহ","টুপি","ইসলামিক উপহার সামগ্রী","ইসলামিক বই"
    )

    // add product conditions e,g ,new, Used, Refurbished

    val conditions = arrayOf(
        "নতুন","ব্যাবহারিত",
    )

    //for choose location
    val locationn = arrayOf("খুলনা", "ঢাকা", "বরিশাল", "চট্টগ্রাম", "ময়মনসিংহ", "রংপুর", "সিলেট", "রাজশাহী")



    fun toast(context: Context,massage: String){
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }


    fun getTimestamp() : Long{
        return System.currentTimeMillis()
    }


    fun formatTimestampDate(timestamp: Long): String{
        val calender = Calendar.getInstance(Locale.ENGLISH)
        calender.timeInMillis = timestamp

        return DateFormat.format("dd/MM/yyyy",calender).toString()
    }



}