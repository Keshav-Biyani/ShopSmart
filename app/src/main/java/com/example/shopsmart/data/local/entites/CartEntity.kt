package com.example.shopsmart.data.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey val id: Int,
    val name: String,
    //val category: String,
    val imageUrl: String,
    val discountedPrice: Double,
    val originalPrice: Double,
    var quantity: Int // Quantity for cart management
)

data class Product(
    val id: Int,
    val name: String,
    val category: String,
    val description : String,
    val price: Double,
    val discountPrice: Double,
    val imageUrl: String,
    val rating: Float,
    val reviews: Int,
    var quantity: Int = 0 // Track quantity locally for UI
)
