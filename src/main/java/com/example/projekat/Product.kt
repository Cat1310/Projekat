package com.example.projekat

import java.io.Serializable

data class Product(
    val id: Int,
    var name: String,
    var price: Double,
    var quantity: Int,
    var deliveryDaysExpress: Int,
    var deliveryDaysStandard: Int,
    var countryOfOrigin: String
) : Serializable
