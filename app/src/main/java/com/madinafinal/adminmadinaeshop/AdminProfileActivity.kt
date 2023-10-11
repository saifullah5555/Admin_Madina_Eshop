package com.madinafinal.adminmadinaeshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.madinafinal.adminmadinaeshop.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }


        binding.NameAdmin.isEnabled = false
        binding.AddressAdmin.isEnabled = false
        binding.EmailAdmin.isEnabled = false
        binding.PhoneAdmin.isEnabled = false
        binding.PasswordAdmin.isEnabled = false

        var isenable = false
        binding.EditeButtonAdmin.setOnClickListener {
            isenable = !isenable

            binding.NameAdmin.isEnabled = isenable
            binding.AddressAdmin.isEnabled = isenable
            binding.EmailAdmin.isEnabled = isenable
            binding.PhoneAdmin.isEnabled = isenable
            binding.PasswordAdmin.isEnabled = isenable
            if(isenable){
                binding.NameAdmin.requestFocus()
            }
        }
    }
}