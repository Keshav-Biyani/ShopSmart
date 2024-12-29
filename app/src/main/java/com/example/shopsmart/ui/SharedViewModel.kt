package com.example.shopsmart.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopsmart.data.local.entites.CartEntity
import com.example.shopsmart.data.local.entites.Product
import com.example.shopsmart.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SharedViewModel @Inject constructor(private  val repository: Repository) : ViewModel() {


    private val _products = MutableStateFlow(
        listOf(
            Product(
                1,
                "Ayuvya i-Gain+"
                ,"Daily Health","Ashwagandha, Amla, Narkachoor + 6 other herbs",
                1099.0,
                749.0,
                "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fnew_image_carousel_of_igain_unisex_5aug_1.webp&w=640&q=75",
                4.5f,
                5673
            ),
            Product(2, "Ayuvya Boobeautiful Oil","Daily Health","Ashwagandha, Amla, Narkachoor + 6 other herbs" , 899.0,  599.0 , "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fbbf_carousel_1slide_6nov2024.webp&w=640&q=75", 4.2f, 1234),
            Product(3, "Ayuvya Boomax | 60 capsules","Daily Health","Ashwagandha, Amla, Narkachoor + 6 other herbs", 965.0, 765.0, "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fayuvya_bmax_new_carousel_13july_1.webp&w=640&q=75", 4.8f, 2145),
            Product(4, "Ayuvya Boobeautiful Oil","Daily Health","Ashwagandha, Amla, Narkachoor + 6 other herbs", 899.0,  599.0 , "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fbbf_carousel_1slide_6nov2024.webp&w=640&q=75", 4.2f, 1234),
        Product(5, "Ayuvya Boomax | 60 capsules","Daily Health","Ashwagandha, Amla, Narkachoor + 6 other herbs", 965.0, 765.0, "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fayuvya_bmax_new_carousel_13july_1.webp&w=640&q=75", 4.8f, 2145)

    )
    )
    val products: StateFlow<List<Product>> = _products

    private val _cartList = MutableStateFlow<List<CartEntity>>(emptyList())
    val cartList : StateFlow<List<CartEntity>> = _cartList

    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount: StateFlow<Int> = _cartItemCount
    private val _totalDiscountedPriceWithQuantity = MutableStateFlow(0.0)
    val totalDiscountedPriceWithQuantity: StateFlow<Double> = _totalDiscountedPriceWithQuantity
    private val _totalOriginalPriceWithQuantity = MutableStateFlow(0.0)
    val totalOriginalPriceWithQuantity: StateFlow<Double> = _totalOriginalPriceWithQuantity

    init {
        syncCartData()
        getCartList()
        fetchCartItemCount()
        fetchTotalPriceData()
    }

    private fun fetchTotalPriceData(){
        viewModelScope.launch {
            repository.getTotalDiscountedPriceWithQuantity()
                .collect{discount->
                    _totalDiscountedPriceWithQuantity.value = discount ?:0.0

                }
        }
        viewModelScope.launch {
            repository.getTotalOriginalPriceWithQuantity()
                .collect{price->
                    _totalOriginalPriceWithQuantity.value = price ?:0.0

                }
        }

    }
    private fun fetchCartItemCount() {
        viewModelScope.launch {
            repository.getTotalNumberOfCart()
                .collect{cartItemCount->
                    _cartItemCount.value=cartItemCount

                }
        }
    }
    private fun getCartList() {
        viewModelScope.launch {
            repository.getCartList()
                .collect { cartList ->
                    _cartList.value = cartList
                }
        }
    }
    fun deleteCartItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.removeFromCart(id)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error deleting dish: ${e.message}")
            }
        }
    }

    fun updateProductQuantity(productId: Int, newQuantity: Int) {
        _products.update { productList ->
            productList.map { product ->
                if (product.id == productId) {
                    product.copy(quantity = newQuantity)
                } else product
            }
        }

        viewModelScope.launch {
            if (newQuantity > 0) {

                val existingItem = repository.getCartList().firstOrNull()?.find { it.id==productId }
                if (existingItem != null) {
                    // Update quantity if the item exists
                    repository.updateCartQuantity(productId,newQuantity)

                } else {
                    // Add the item to the cart if it doesn't exist
                    val product = _products.value.first { it.id == productId }
                    repository.insertCartItem(
                        CartEntity(product.id,product.name,product.imageUrl,product.discountPrice,product.price,newQuantity)
                    )
                }
            } else {

                //remove from cart if quantity is less than or equal to zero
                repository.removeFromCart(productId)
            }
        }
    }
    fun syncCartData() {
        viewModelScope.launch {

            repository.getCartList().collect{cartItems->
                _products.update {productList->
                    productList.map{product->
                        val cartItem = cartItems.find { it.id == product.id }
                        product.copy(quantity = cartItem?.quantity ?: 0)
                    }
                }
            }

        }
    }
}