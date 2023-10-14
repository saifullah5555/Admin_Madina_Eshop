package com.madinafinal.adminmadinaeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.madinafinal.adminmadinaeshop.databinding.ActivitySignUpBinding
import com.madinafinal.adminmadinaeshop.model.UserModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var userName: String
    private lateinit var nameOfStore: String
    private lateinit var email: String
    private lateinit var password: String

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initilize firebase Auth
        auth = Firebase.auth

        //initilize firebase database
        database = Firebase.database.reference


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

        binding.ragisterBtn.setOnClickListener {
            //get text from editetext
            userName = binding.nameUserSignUp.text.toString().trim()
            nameOfStore = binding.nameStoreSignUp.text.toString().trim()
            email = binding.emailAddressSignUp.text.toString().trim()
            password = binding.passwordSignUp.text.toString().trim()

            //feild jate user khali rekhe signup korte na pare se jonno niser code
            if (userName.isBlank() || nameOfStore.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                CreateAccount(email, password)
            }

        }

    }

    private fun CreateAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                SaveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "CreateAccount: Failure", task.exception)
            }

        }
    }

    //Save data in to database
    private fun SaveUserData() {
        //get text from editetext
        userName = binding.nameUserSignUp.text.toString().trim()
        nameOfStore = binding.nameStoreSignUp.text.toString().trim()
        email = binding.emailAddressSignUp.text.toString().trim()
        password = binding.passwordSignUp.text.toString().trim()
        val user = UserModel(userName, nameOfStore, email, password)
        val userid: String = FirebaseAuth.getInstance().currentUser!!.uid
        // Save user data Firebase database
        database.child("user").child(userid).setValue(user)
    }
}