package com.madinafinal.adminmadinaeshop

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.madinafinal.adminmadinaeshop.adapter.AdapterImagePiked55
import com.madinafinal.adminmadinaeshop.databinding.ActivitySliderUploadBinding
import com.madinafinal.adminmadinaeshop.model.ModelImagePicked55

// this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root
// this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root
// this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root

class SliderUploadActivity55 : AppCompatActivity() {
    private val binding: ActivitySliderUploadBinding by lazy {
        ActivitySliderUploadBinding.inflate(layoutInflater)
    }

    private companion object {
        private const val TAG = "ADD_CREATE_TAG"
    }

    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    // for image piked
    private var imageUri: Uri? = null

    private lateinit var imagePikedArrayList: ArrayList<ModelImagePicked55>
    private lateinit var adapterImagePiked: AdapterImagePiked55

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()


        //  setup and set the categories adapter to the category input Field i.c categoriesAtc
        val adapterCategories = ArrayAdapter(
            this,
            R.layout.row_catagorry_etc,
            Utils55.categorys
        )
        binding.categoryAct.setAdapter(adapterCategories)

        //  setup and set the Location adapter to the Location input Field i.c categoriesAtc
        val adapterLocation = ArrayAdapter(
            this,
            R.layout.row_location_etc,
            Utils55.locationn
        )
        binding.locationAct.setAdapter(adapterLocation)



        //  setup and set the condition adapter to the condition input Field i.c conditionAtc
        val adapterCondition = ArrayAdapter(
            this,
         R.layout.row_condition_act,
            Utils55.conditions
        )
        binding.conditionAct.setAdapter(adapterCondition)

        //init imagePikedArrayList
        imagePikedArrayList = ArrayList()
        // load image
        loadImage()

        binding.toolBarBackBtn.setOnClickListener {
            onBackPressed()
        }

        binding.toolbarAddImageBtn.setOnClickListener {
            showImagePikOption()
        }

        binding.postAddBtn.setOnClickListener {

            validateDate()

        }


    }

    private fun loadImage() {
        Log.d(TAG, "loadImage: ")

        adapterImagePiked = AdapterImagePiked55(this, imagePikedArrayList)
        binding.imageRV.adapter = adapterImagePiked
    }

    private fun showImagePikOption() {
        Log.d(TAG, "showImagePikOption: ")
        val popupMenu = PopupMenu(this, binding.toolbarAddImageBtn)
        popupMenu.menu.add(Menu.NONE, 1, 1, "Camera")
        popupMenu.menu.add(Menu.NONE, 2, 2, "Gallery")

        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { item ->

            val itemId = item.itemId
            if (itemId == 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                    val cameraPermission = arrayOf(Manifest.permission.CAMERA)
                    requestCameraPermission.launch(cameraPermission)
                } else {

                    val cameraPermission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    requestCameraPermission.launch(cameraPermission)
                }
            } else if (itemId == 2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                    pickImageGallery()
                } else {

                    val storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
                    requestStoragePermission.launch(storagePermission)
                }

            }

            true
        }
    }

    private val requestStoragePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            Log.d(TAG, "requestStoragePermission: isGranted: $isGranted")
            // lets check if permission is granted or not
            if (isGranted) {
                pickImageGallery()
            } else {
                Utils55.toast(this, "Storage Permission denied...")
            }
        }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            Log.d(TAG, "requestCameraPermission: result: $result")
            // lets check if permission is granted or not
            var areAllGranted = true
            for (isGranted in result.values) {

                areAllGranted = areAllGranted && isGranted
            }

            if (areAllGranted) {
                pickImageCamera()
            } else {
                Utils55.toast(this, "Camera or Storage or both Permission denied...")
            }

        }

    private fun pickImageGallery() {
        Log.d(TAG, "pickImageGallery: ")

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private fun pickImageCamera() {
        Log.d(TAG, "pickImageCamera: ")
        val contentValues = ContentValues()

        contentValues.put(MediaStore.Images.Media.TITLE, "TEMP_IMAGE_TITLE")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "TEMP_IMAGE_DESCRIPTION")

        imageUri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)


        cameraActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(TAG, "galleryActivityResultLauncher: ")

            // check if image is piked or not
            if (result.resultCode == Activity.RESULT_OK) {

                //get data from result param
                val data = result.data

                //get uri of image piked
                imageUri = data!!.data
                Log.d(TAG, "galleryActivityResultLauncher: imageUri: $imageUri ")

                // timestamp will be used as id of the image piked
                val timestamp = "${Utils55.getTimestamp()}"

                // setup model for image . param 1 is id ,param 2 is imageUri, param 3 is imageUrl ,fromInternet
                val modelImagePicked = ModelImagePicked55(timestamp, imageUri, null, false)

                // add model to the imagePikedArrayList
                imagePikedArrayList.add(modelImagePicked)

                // reload the image
                loadImage()
            } else {

                //Cancelled
                Utils55.toast(this, "Cancelled...!")
            }

        }

    private val cameraActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(TAG, "cameraActivityResultLauncher: ")
            //Check if image is piked or not
            if (result.resultCode == Activity.RESULT_OK) {

                // no need to get image uri here we will have it in pikeImageCamera() function
                Log.d(TAG, "cameraActivityResultLauncher: imageUri $imageUri")

                // timestamp will be used as id of the image piked
                val timestamp = "${Utils55.getTimestamp()}"

                // setup model for image . param 1 is id ,param 2 is imageUri, param 3 is imageUrl ,fromInternet
                val modelImagePiked = ModelImagePicked55(timestamp, imageUri, null, false)

                // add model to the imagePikedArrayList
                imagePikedArrayList.add(modelImagePiked)

                // reload the image
                loadImage()

            } else {

                //Cancelled
                Utils55.toast(this, "Cancelled...!")
            }
        }


    private var brand = ""
    private var category = ""
    private var condition = ""
    private var address = ""
    private var price = ""
    private var title = ""
    private var description = ""
    private var longDescription = ""
    private var latitude = 0.0
    private var longitude = 0.0


    private fun validateDate() {

        Log.d(TAG, "validateDate: ")

        brand = binding.brandEt.text.toString().trim()
        category = binding.categoryAct.text.toString().trim()
        condition = binding.conditionAct.text.toString().trim()
        address = binding.locationAct.text.toString().trim()
        price = binding.priceEt.text.toString().trim()
        title = binding.titleEt.text.toString().trim()
        description = binding.descriptionEt.text.toString().trim()
        longDescription = binding.longDescriptionEt.text.toString().trim()


        //validate data
        if (brand.isEmpty()) {
            //no brand entered in brandEt, show error in brandEt and focus
            binding.brandEt.error = "Enter Brand"
            binding.brandEt.requestFocus()
        } else if (category.isEmpty()) {
            //no brand entered in brandEt, show error in brandEt and focus
            binding.categoryAct.error = "Choose Category"
            binding.categoryAct.requestFocus()
        } else if (condition.isEmpty()) {
            //no brand entered in brandEt, show error in brandEt and focus
            binding.conditionAct.error = "Choose Condition"
            binding.conditionAct.requestFocus()
        } else if (address.isEmpty()) {
            //no brand entered in brandEt, show error in brandEt and focus
            binding.locationAct.error = "Choose Location"
            binding.locationAct.requestFocus()
        } else if (title.isEmpty()) {
            //no brand entered in brandEt, show error in brandEt and focus
            binding.titleEt.error = "Choose Location"
            binding.titleEt.requestFocus()
        } else if (address.isEmpty()) {
            //no brand entered in brandEt, show error in brandEt and focus
            binding.descriptionEt.error = "Choose Location"
            binding.descriptionEt.requestFocus()
        } else if (longDescription.isEmpty()) {
            //no brand entered in brandEt, show error in brandEt and focus
            binding.longDescriptionEt.error = "Choose Location"
            binding.longDescriptionEt.requestFocus()
        } else {
            postAd()
        }

    }

    private fun postAd() {
        Log.d(TAG, "postAd: ")

        progressDialog.setMessage("Publishing Ad")
        progressDialog.show()

        val timeStamp = Utils55.getTimestamp()

        val refAd = FirebaseDatabase.getInstance().getReference("menu")

        val keyId = refAd.push().key

        // setup data to add in firebase database
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$keyId"
        hashMap["uid"] = "${firebaseAuth.uid}"
        hashMap["beand"] = "$brand"
        hashMap["category"] = "$category"
        hashMap["condition"] = "$condition"
        hashMap["address"] = "$address"
        hashMap["price"] = "$price"
        hashMap["description"] = "$description"
        hashMap["longdescription"] = "$longDescription"
        hashMap["title"] = "$title"
        hashMap["status"] = "${Utils55.AD_STATUS_AVAILABLE}"
        hashMap["timestamp"] = timeStamp
        hashMap["latitude"] = latitude
        hashMap["longitude"] = longitude

        // set data to firebase database . ads  -> AdDataJSON
        refAd.child(keyId!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                Log.d(TAG, "postAd: Ad Published")
                uploadImagesStorage(keyId)

            }
            .addOnFailureListener {e->
                Log.d(TAG, "postAd: ",e)
                progressDialog.dismiss()
                Utils55.toast(this,"Failed due to ${e.message}")
                
            }


    }

    private fun uploadImagesStorage(adId: String){

        //there are multiple images in imagePickedArrayList, loop to upload all
        for (i in imagePikedArrayList.indices){

            //get model from the current position of the imagePickedArrayList
            val modelImagePiked = imagePikedArrayList[i]

            //for name of the image in firebase storage
            val imageName = modelImagePiked.id

            //path and name of the image in firebase storage
            val filepathAndName = "menu_images/$imageName"

            val imageIndexForProgress = i + 1

            //Storage reference with filePathAndName
            val storageReference = FirebaseStorage.getInstance().getReference(filepathAndName)

            storageReference.putFile(modelImagePiked.imageUri!!)

                .addOnProgressListener { snapShot->

                    //calculate the current progress of me image being uploaded
                    val progress = 100.0 *snapShot.bytesTransferred / snapShot.totalByteCount

                    Log.d(TAG, "uploadImagesStorage: progress: $progress")

                    val massage = "Uploading $imageIndexForProgress of ${imagePikedArrayList.size} image... progress ${progress.toInt()}"

                    Log.d(TAG, "uploadImagesStorage: massage: $massage")
                    progressDialog.setMessage(massage)
                    progressDialog.show()


                }
                .addOnSuccessListener {taskSnapShot ->

                    //image uploaded get url of uploaded image
                    Log.d(TAG, "uploadImagesStorage: success")

                    val uriTask = taskSnapShot.storage.downloadUrl

                    while (!uriTask.isSuccessful);
                    val uploadImageUrl = uriTask.result


                    if (uriTask.isSuccessful){

                        val hashMap = HashMap<String, Any>()

                        hashMap["id"] = "${modelImagePiked.id}"
                        hashMap["imageUrl"] = "$uploadImageUrl"

                        val ref = FirebaseDatabase.getInstance().getReference("menu")
                        ref.child(adId).child("images")
                            .child(imageName)
                            .updateChildren(hashMap)

                    }

                    progressDialog.dismiss()


                }
                .addOnFailureListener{e ->

                    //failed to upload image
                    Log.d(TAG, "uploadImagesStorage: ",e)
                    progressDialog.dismiss()

                }
        }

    }
}