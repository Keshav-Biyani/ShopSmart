package com.example.shopsmart.ui.screen.productDetailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.shopsmart.data.local.entites.Product
import com.example.shopsmart.ui.screen.productListScreen.component.CartIcon

@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    quantity: Int,
    onIncreaseQuantity: (Int) -> Unit,
    onDecreaseQuantity: (Int) -> Unit,
    cartItemCount :Int,// Pass the selected product details
    onBackClick: () -> Unit, // Callback to handle back navigation
    onAddToCart: (Int) -> Unit,
    onNavigation:()->Unit, // Callback to add product to cart
    onBuyNowClick: (Int) -> Unit // Callback for "Buy Now" action
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") }  ,actions = {
                    CartIcon(quantity = cartItemCount) { onNavigation() }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.LightGray),
                contentScale = ContentScale.FillBounds
            )

            // Product Information
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.category ,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "₹${product.discountPrice}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "₹${product.price}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            textDecoration = TextDecoration.LineThrough
                        ),
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Product Rating and Reviews
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFA500)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = product.rating.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "(${product.reviews} reviews)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Product Description
                Text(

                    text = "Description",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(

                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (quantity > 0) {
                        Row(verticalAlignment = Alignment.CenterVertically
                            ,   modifier = Modifier
                            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)) // Rounded corners for the entire row
                            .padding(8.dp) ) {
//                            Button(onClick = { onDecreaseQuantity(product.id) }) { Text("-") }
//                            Text(text = "$quantity", modifier = Modifier.padding(horizontal = 8.dp))
//                            Button(onClick = { onIncreaseQuantity(product.id) }) { Text("+") }
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                modifier = Modifier
//                                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)) // Rounded corners for the entire row
//                                    .padding(8.dp) // Adding padding around the row
//                            ) {
                                // Decrease button
                                IconButton(
                                    onClick = { onDecreaseQuantity(product.id) }, // Ensure onDecrease is invoked properly
                                    modifier = Modifier
                                        .size(30.dp) // Consistent button size
                                        .padding(4.dp) // Adjust padding inside the button
                                    //   .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(50)) // Background color
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Remove, // Changed to 'Remove' for better semantics
                                        contentDescription = "Decrease Quantity",
                                        //  tint = Color.White, // Contrast tint for better visibility
                                        modifier = Modifier.size(24.dp) // Adjust icon size
                                    )
                                }

                                // Display Quantity
                                Text(
                                    text = quantity.toString(),
                                    modifier = Modifier.padding(horizontal = 12.dp),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )

                                // Increase button
                                IconButton(
                                    onClick = {onIncreaseQuantity(product.id) }, // Ensure onIncrease is invoked properly
                                    modifier = Modifier
                                        .size(30.dp) // Consistent button size
                                        .padding(4.dp) // Adjust padding inside the button
                                    //  .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(50)) // Background color
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Increase Quantity",
                                        //   tint = Color.White, // Contrast tint for better visibility
                                        modifier = Modifier.size(24.dp) // Adjust icon size
                                    )
                                }
                            }

                      //  }
                    } else {
                        Button(
                            onClick = { onAddToCart(product.id) },
                            colors = ButtonDefaults.buttonColors(Color(0xFFB5D334)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "ADD TO CART", color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onBuyNowClick(product.id) },
                        colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "BUY NOW", color = Color.White)
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun Preview(){
    ProductDetailScreen(product =  Product(
        1,
        "Ayuvya i-Gain+","Daily Health","Ashwagandha, Amla, Narkachoor + 6 other herbs",
        1099.0,
        749.0,
        "https://ayuvya.com/_next/image?url=https%3A%2F%2Fstorage.googleapis.com%2Fayuvya_images%2Fproduct_image%2Fnew_image_carousel_of_igain_unisex_5aug_1.webp&w=256&q=75",
        4.5f,
        5673
    ),2,{},{},2, onBackClick = { /*TODO*/ }, {}, {}) {

    }
}
