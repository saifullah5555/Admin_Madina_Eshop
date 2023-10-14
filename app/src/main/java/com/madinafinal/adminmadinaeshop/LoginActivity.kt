package com.madinafinal.adminmadinaeshop

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.madinafinal.adminmadinaeshop.databinding.ActivityLoginBinding
import com.madinafinal.adminmadinaeshop.model.UserModel

class LoginActivity : AppCompatActivity() {
    private var userName: String? = null
    private var nameOfStore: String? = null
    private lateinit var email: String
    private lateinit var password: String

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSgnInClient: GoogleSignInClient

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // ekhane default_web_client_id) Asenai Problem
            // ekhane default_web_client_id) Asenai Problem
            .requestIdToken("724033937918-m1mqasc0iojh00iae8s3su9lshh60ce0.apps.googleusercontent.com").requestEmail().build()
        // .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        //initilize firebase Auth
        auth = Firebase.auth

        //initilize Google SignIn Client
        googleSgnInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        //initilize firebase database
        database = Firebase.database.reference

        binding.AdminLoginBtn.setOnClickListener {
            //get text from editetext
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()
            //feild jate user khali rekhe signup korte na pare se jonno niser code
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                CreateAccount(email, password)
            }
        }
        binding.GoogleBtn.setOnClickListener {
            val signIntent = googleSgnInClient.signInIntent

            launcher.launch(signIntent)
        }

        binding.RagisterButtonText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun CreateAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user: FirebaseUser? = auth.currentUser
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                updateUi(user)

            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        Toast.makeText(this, " Create User & Login Successful", Toast.LENGTH_SHORT)
                            .show()
                        SaveUserData()
                        updateUi(user)
                    } else {
                        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                        Log.d("Account", "CreateUserAccount: Authentication Failed", task.exception)
                    }
                }
            }
        }
    }

    private fun SaveUserData() {
        //get text from editetext
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = UserModel(userName, nameOfStore, email, password)
        val userid: String? = FirebaseAuth.getInstance().currentUser?.uid
        userid?.let {
            database.child("user").child(it).setValue(user)
        }


    }


    // launcher for google sign in
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount = task.result

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                    auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            // successfully sign in with google
                            Toast.makeText(
                                this,
                                "Successfully sign-in with google",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUi(authTask.result?.user)
                        } else {
                            Toast.makeText(this, "Google Sign-in failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Google Sign-in failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    // Check if user allredy loged in
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}