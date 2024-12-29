package com.example.shopsmart.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shopsmart.data.local.entites.CartEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDao {

    @Insert
    suspend fun insertCartItem(cartEntity: CartEntity)

    @Query("UPDATE cart SET quantity = :quantity WHERE id = :productId")
    suspend fun updateQuantity(productId: Int, quantity: Int)


    @Query("DELETE FROM cart WHERE Id = :productId")
    suspend fun removeFromCart(productId: Int)

    @Query(""" SELECT * FROM cart""")
    fun getCartList() : Flow<List<CartEntity>>

    @Query("""SELECT COUNT(id) FROM cart""")
    fun getTotalNumberOfCart() : Flow<Int>
    @Query("SELECT COALESCE(SUM(discountedPrice * quantity), 0.0) FROM cart")
    fun getTotalDiscountedPriceWithQuantity(): Flow<Double>

    @Query("SELECT COALESCE(SUM(originalPrice * quantity),0.0) FROM cart")
    fun getTotalOriginalPriceWithQuantity(): Flow<Double>
}