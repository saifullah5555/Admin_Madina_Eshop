package com.madinafinal.adminmadinaeshop.model

import android.net.Uri

class ModelImagePicked55 {
    // this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root
    // this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root
    // this activity / model / adapter is for multiple image work, and other method/ other channel. not neat root

    var id = ""
    var imageUri: Uri? = null
    var imageUrl: String? = null
    var fromInternet = false

    constructor()
    constructor(id: String, imageUri: Uri?, imageUrl: String?, fromInternet: Boolean) {
        this.id = id
        this.imageUri = imageUri
        this.imageUrl = imageUrl
        this.fromInternet = fromInternet
    }


}