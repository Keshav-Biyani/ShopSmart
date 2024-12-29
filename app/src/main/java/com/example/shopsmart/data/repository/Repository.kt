package com.example.shopsmart.data.repository

import com.example.shopsmart.data.local.database.CartDatabase
import com.example.shopsmart.data.local.entites.CartEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository@Inject constructor(private val db :CartDatabase) {

        fun getCartList() : Flow<List<CartEntity>>{
                return db.cartDao().getCartList()
        }

        fun getTotalNumberOfCart():Flow<Int>{
            return db.cartDao().getTotalNumberOfCart()
        }

    fun getTotalDiscountedPriceWithQuantity(): Flow<Double>{
        return db.cartDao().getTotalDiscountedPriceWithQuantity()
    }


    fun getTotalOriginalPriceWithQuantity(): Flow<Double>{
        return db.cartDao().getTotalOriginalPriceWithQuantity()
    }

        suspend fun removeFromCart(productId : Int){
            db.cartDao().removeFromCart(productId)
        }

        suspend fun insertCartItem(cartItem : CartEntity){
            db.cartDao().insertCartItem(cartItem)
        }

        suspend fun updateCartQuantity(productId: Int,quantity : Int){
            db.cartDao().updateQuantity(productId,quantity)
        }


}