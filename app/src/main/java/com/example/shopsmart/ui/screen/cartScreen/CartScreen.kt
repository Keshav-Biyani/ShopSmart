package com.example.shopsmart.ui.screen.cartScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopsmart.R
import com.example.shopsmart.data.local.entites.CartEntity
import com.example.shopsmart.ui.screen.component.EmptyList
import com.example.shopsmart.ui.SharedViewModel
import com.example.shopsmart.ui.screen.cartScreen.component.CartItem





@Composable
fun CartScreenStateful(viewModel: SharedViewModel, onStartShopping: () -> Unit,onProductClick:(Int)->Unit, onBackClick: () -> Unit) {
    val cartList by viewModel.cartList.collectAsState()
    val subtotal by viewModel.totalDiscountedPriceWithQuantity.collectAsState()
    val discount by viewModel.totalOriginalPriceWithQuantity.collectAsState()

    CartScreenUI(
        cartList = cartList,
        onBackClick = onBackClick,
        onStartShopping = onStartShopping,
        onQuantityChange = { cartItemId, quantity ->
            viewModel.updateProductQuantity(cartItemId, quantity)
        },
        onDeleteCartItem = { cartItemId ->
            viewModel.deleteCartItem(cartItemId)
        },subtotal,
        discount,{productId->
            onProductClick(productId)

        }
        ,{}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenUI(
    cartList: List<CartEntity>,                  // Cart items list
    onBackClick: () -> Unit,                    // Callback for back navigation
    onStartShopping: () -> Unit,                // Callback for empty cart action
    onQuantityChange: (Int, Int) -> Unit,       // Callback for quantity changes
    onDeleteCartItem: (Int) -> Unit,            // Callback for deleting cart items
    subtotal: Double,                              // Subtotal amount
    discount: Double,
    onProductClick:(Int)->Unit,// Discount amount
    onCheckout: () -> Unit                      // Callback for checkout action
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cart") },
                navigationIcon = {

                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Gray
                        )
                    }
                }
            )
        },
        bottomBar = {
            if(cartList.isNotEmpty()){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Subtotal", style = MaterialTheme.typography.bodyLarge)
                    Column(horizontalAlignment = AbsoluteAlignment.Right) {
                        Text(text = "₹$subtotal", style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = "₹${discount}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray
                            ),
                            textAlign = TextAlign.Right
                        )
                    }
                   // Text(text = "₹$subtotal", style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Shipping and taxes calculated at checkout",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onCheckout() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "CHECKOUT")
                }
            }}
        }
    ) { padding ->
        if (cartList.isEmpty()) {
            EmptyList(nameOfList = "Cart", image = R.drawable.empty_shopping) {
                onStartShopping()
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
                items(cartList) { cartItem ->
                    CartItem(
                        cartItem,
                        onQuantityChange = { quantity ->
                            onQuantityChange(cartItem.id, quantity)
                        },
                        onProductClick = {
                            onProductClick(cartItem.id)
                        }
                    ) {
                        onDeleteCartItem(cartItem.id)
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun CartScreenUIPreview(){
    CartScreenUI(
        cartList = emptyList(),
        onBackClick = { /*TODO*/ },
        onStartShopping = { /*TODO*/ },
        onQuantityChange = { cartItemId, quantity ->
            println("Quantity changed for item $cartItemId: $quantity")
        },
        onDeleteCartItem = { cartItemId ->
            println("Item deleted: $cartItemId")
        },45000.0,4500.0,{},{}
    )
}

