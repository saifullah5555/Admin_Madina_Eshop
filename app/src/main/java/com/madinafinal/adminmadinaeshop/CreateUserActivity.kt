package com.madinafinal.adminmadinaeshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.madinafinal.adminmadinaeshop.databinding.ActivityCreateUserBinding

class CreateUserActivity : AppCompatActivity() {
    private val bindind: ActivityCreateUserBinding by lazy {
        ActivityCreateUserBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindind.root)

        bindind.backButton.setOnClickListener {
            finish()
        }
    }
}