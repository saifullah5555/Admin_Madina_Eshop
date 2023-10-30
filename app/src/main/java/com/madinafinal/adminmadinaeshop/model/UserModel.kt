package com.madinafinal.adminmadinaeshop.model

data class UserModel(
    val name:String? = null,
    val nameOfStore:String? = null,
    val email:String? = null,
    val password:String? = null,
    var phone: String? = null,
    var address: String? = null
)
