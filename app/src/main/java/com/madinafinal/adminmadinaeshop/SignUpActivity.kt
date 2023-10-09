package com.madinafinal.adminmadinaeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.madinafinal.adminmadinaeshop.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //for choose location
        val locationList =
            arrayOf("খুলনা", "ঢাকা", "বরিশাল", "চট্টগ্রাম", "ময়মনসিংহ", "রংপুর", "সিলেট", "রাজশাহী")
        //choose location er jonno adapter Banano holo
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        // ebar choose location find kore jora lagano holo
        val AutoCompleteTextView = binding.listOfLocation
        AutoCompleteTextView.setAdapter(adapter)
        //hoe gelo choose Location er kaj Alhamdulillah

        binding.BackLoginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

            binding.RagisterBtn.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

    }
}