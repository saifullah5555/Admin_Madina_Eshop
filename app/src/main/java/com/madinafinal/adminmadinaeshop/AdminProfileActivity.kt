package com.madinafinal.adminmadinaeshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.madinafinal.adminmadinaeshop.databinding.ActivityAdminProfileBinding
import com.madinafinal.adminmadinaeshop.model.UserModel

class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference = database.reference.child("user")

        binding.backButton.setOnClickListener {
            finish()
        }
        binding.SaveButtonAdmin.setOnClickListener {
            updateUserData()
        }


        binding.NameAdmin.isEnabled = false
        binding.AddressAdmin.isEnabled = false
        binding.EmailAdmin.isEnabled = false
        binding.PhoneAdmin.isEnabled = false
        binding.PasswordAdmin.isEnabled = false
        binding.SaveButtonAdmin.isEnabled = false


        var isenable = false
        binding.EditeButtonAdmin.setOnClickListener {
            isenable = !isenable

            binding.NameAdmin.isEnabled = isenable
            binding.AddressAdmin.isEnabled = isenable
            binding.EmailAdmin.isEnabled = isenable
            binding.PhoneAdmin.isEnabled = isenable
            binding.PasswordAdmin.isEnabled = isenable
            binding.SaveButtonAdmin.isEnabled = isenable

            if (isenable) {
                binding.NameAdmin.requestFocus()
            }
        }
        retrieveUserData()
    }


    private fun retrieveUserData() {

        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null) {
            val userReference = adminReference.child(currentUserUid)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var ownerName = snapshot.child("name").getValue()
                        var ownerEmail = snapshot.child("email").getValue()
                        var ownerAddress = snapshot.child("address").getValue()
                        var ownerPhone = snapshot.child("phone").getValue()
                        var ownerPassword = snapshot.child("password").getValue()

                        setDataToTextView(
                            ownerName,
                            ownerEmail,
                            ownerAddress,
                            ownerPhone,
                            ownerPassword
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }


    }

    private fun setDataToTextView(
        ownerName: Any?,
        ownerEmail: Any?,
        ownerAddress: Any?,
        ownerPhone: Any?,
        ownerPassword: Any?
    ) {
        binding.NameAdmin.setText(ownerName.toString())
        binding.AddressAdmin.setText(ownerAddress.toString())
        binding.EmailAdmin.setText(ownerEmail.toString())
        binding.PhoneAdmin.setText(ownerPhone.toString())
        binding.PasswordAdmin.setText(ownerPassword.toString())
    }

    private fun updateUserData() {
        var updateName = binding.NameAdmin.text.toString()
        var updateAddress = binding.AddressAdmin.text.toString()
        var updateEmail = binding.EmailAdmin.text.toString()
        var updatePhone = binding.PhoneAdmin.text.toString()
        var updatePassword = binding.PasswordAdmin.text.toString()
        val currentUserUid = auth.currentUser?.uid

        if (currentUserUid != null){
            val userReference = adminReference.child(currentUserUid)
            userReference.child("name").setValue(updateName)
            userReference.child("email").setValue(updateEmail)
            userReference.child("phone").setValue(updatePhone)
            userReference.child("password").setValue(updatePassword)
            userReference.child("address").setValue(updateAddress)

            Toast.makeText(this, "profile updated successfully", Toast.LENGTH_SHORT).show()

            // update the email or password for firebase Authentication
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)

        } else {
            Toast.makeText(this, "profile not updated", Toast.LENGTH_SHORT).show()
        }
    }

}