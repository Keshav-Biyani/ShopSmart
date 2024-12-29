package com.example.shopsmart.ui.screen.productListScreen


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopsmart.data.local.entites.Product
import com.example.shopsmart.ui.SharedViewModel
import com.example.shopsmart.ui.screen.productListScreen.component.CartIcon
import com.example.shopsmart.ui.screen.productListScreen.component.ProductCard
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.unit.dp


@Composable
fun ProductListScreenStateFull(viewModel: SharedViewModel,onProductClick :(Int)->Unit, onNavigation: () -> Unit) {
    val productList by viewModel.products.collectAsState()
    val cartItemCount by viewModel.cartItemCount.collectAsState()

    ProductListScreenUI(
        productList = productList,
        cartItemCount = cartItemCount,
        onIncreaseQuantity = { productId ->
            val product = productList.find { it.id == productId }
            if (product != null) {
                viewModel.updateProductQuantity(productId, product.quantity + 1)
            }
        },
        onDecreaseQuantity = { productId ->
            val product = productList.find { it.id == productId }
            if (product != null && product.quantity > 0) {
                viewModel.updateProductQuantity(productId, product.quantity - 1)
            }
        },
        onAddToCart = { productId ->
            val product = productList.find { it.id == productId }
            if (product != null) {
                viewModel.updateProductQuantity(productId, product.quantity + 1)
            }
        },
        onProductClick ={productId->
            onProductClick(productId)
        },
        onNavigation = onNavigation
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductListScreenUI(
    productList: List<Product>,
    cartItemCount: Int,
    onIncreaseQuantity: (Int) -> Unit,
    onDecreaseQuantity: (Int) -> Unit,
    onAddToCart: (Int) -> Unit,
    onProductClick : (Int)->Unit,
    onNavigation: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products") },
                actions = {
                    CartIcon(quantity = cartItemCount) { onNavigation() }
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Set to 2 items per row
            contentPadding = padding
        ) {
            items(productList) { product ->
                ProductCard(
                    quantity = product.quantity,
                    onIncrease = { onIncreaseQuantity(product.id) },
                    onDecrease = { onDecreaseQuantity(product.id) },
                    imageUrl = product.imageUrl,
                    category = product.category,
                    rating = product.rating.toString(),
                    reviews = product.reviews.toString(),
                    productName = product.name,
                    discountedPrice = "₹${product.discountPrice}",
                    originalPrice = product.price.toString(),
                    onAddClick = { onAddToCart(product.id) },
                    onProductClick = { onProductClick(product.id) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenUIPreview(){
    ProductListScreenUI(listOf(
        Product(
            1,
            "Ayuvya i-Gain+","Daily Health","Ashwagandha, Amla, Narkachoor + 6 other herbs",
            1099.0,
            749.0,
            "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fnew_image_carousel_of_igain_unisex_5aug_1.webp&w=256&q=75",
            4.5f,
            5673
        ),
        Product(2, "Ayuvya Boobeautiful Oil","Daily Health","Ashwagandha, Amla, Narkachoor + 6 other herbs", 899.0,  599.0 , "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fbbf_carousel_1slide_6nov2024.webp&w=256&q=75", 4.2f, 1234),
        Product(3, "Ayuvya Boomax | 60 capsules","Daily Health","Ashwagandha, Amla, Narkachoor + 6 other herbs", 965.0, 765.0, "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fayuvya_bmax_new_carousel_13july_1.webp&w=256&q=75", 4.8f, 2145)
    ),2,{},{},{},{},{})
}

