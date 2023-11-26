package com.madinafinal.adminmadinaeshop


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.madinafinal.adminmadinaeshop.databinding.ActivityAddItemBinding
import com.madinafinal.adminmadinaeshop.model.AllMenu

class AddItemActivity : AppCompatActivity() {


    //foodItem Details
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private var foodImageUri: Uri? = null
    private var foodImageUri2: Uri? = null
    private lateinit var foodDecription: String
    private lateinit var foodIngradint: String

    //Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //initilize firebase Auth
        auth = FirebaseAuth.getInstance()


        //initilize firebase
        database = FirebaseDatabase.getInstance()


        //  select images form gallery
        binding.selectimage.setOnClickListener {

            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))

        }

        binding.button.setOnClickListener {
            val intent = Intent(this,SliderUploadActivity55::class.java)
            startActivity(intent)
        }



        binding.AddItemButton.setOnClickListener {
            //get data from fields
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDecription = binding.Decription.text.toString().trim()
            foodIngradint = binding.Ingradint.text.toString().trim()
            if (!(foodName.isBlank() || foodPrice.isBlank() || foodDecription.isBlank() || foodIngradint.isBlank())) {

                uploadData()

                Toast.makeText(this, "Item Add Successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show()

            }


        }

        binding.backButton.setOnClickListener {
            finish()
        }


    }

    private fun uploadData() {
        // get reference to the "menu" node in the database
        val menuRef = database.getReference("menu")
        //Genaret a unique key for the new menu item
        val newItemKey = menuRef.push().key

        if (foodImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)
           imageRef.putFile(foodImageUri2!!)






            uploadTask.addOnSuccessListener {

                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->


                    // Create a new menu item
                    val newItem = AllMenu(
                        newItemKey,
                        foodName = foodName,
                        foodPrice = foodPrice,

                        foodImage = downloadUrl.toString(),
                        foodImage2 = downloadUrl.toString(),
                        foodImage3 = downloadUrl.toString(),

                        foodsDecription = foodDecription,
                        foodIngradint = foodIngradint
                    )
                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "data uploaded successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(
                                    this,
                                    "data uploaded failed",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                    }
                }


            }



                .addOnFailureListener {
                    Toast.makeText(this, "image upload failed", Toast.LENGTH_SHORT).show()
                }

        } else {
            Toast.makeText(this, "please select an image", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                binding.selectedimage.setImageURI(uri)
                foodImageUri = uri
            }
        }


    val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
            // Callback is invoked after the user selects media items or closes the photo picker.

            if (uris.isNotEmpty()) {
                // Display the first selected image
                binding.selectedimage.setImageURI(uris[0])
                binding.imageView10.setImageURI(uris[1])

                // Assign the selected URIs to the respective variables
                binding.apply {
                    foodImageUri = uris[0]
                    if (uris.size >= 2) {
                        foodImageUri2 = uris[1]
                   }
                }
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }


}

