package com.example.shopsmart.ui.screen.productListScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.shopsmart.data.local.entites.Product

@Composable
fun ProductCard(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    imageUrl: String,
    category: String,
    rating: String,
    reviews: String,
    productName: String,
    discountedPrice: String,
    originalPrice: String,
    onAddClick: () -> Unit,
    onProductClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onProductClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column() {
            // Product Image
            Image(
                painter = rememberAsyncImagePainter(imageUrl, contentScale = ContentScale.Crop),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp), // Adjust height to fit the grid layout
                contentScale = ContentScale.FillBounds
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(4.dp))

                // Product Details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = rating,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFFFA500)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "($reviews)",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = productName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = discountedPrice,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = originalPrice,
                        style = MaterialTheme.typography.bodySmall.copy(
                            textDecoration = TextDecoration.LineThrough
                        ),
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (quantity > 0) {
                        Row(verticalAlignment = Alignment.CenterVertically
                            ,   modifier = Modifier
                                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)) // Rounded corners for the entire row
                                .padding(8.dp) ) { IconButton(
                            onClick = { onDecrease ()}, // Ensure onDecrease is invoked properly
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
                                onClick = {onIncrease() }, // Ensure onIncrease is invoked properly
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
                    } else {
                        Button(
                            onClick = onAddClick,
                            colors = ButtonDefaults.buttonColors(Color(0xFFB5D334)),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp)
                        ) {
                            Text(text = "ADD +", color = Color.White)
                        }
                    }
//                    Button(
//                        onClick = onBuyNowClick,
//                        colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50)),
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(start = 4.dp)
//                    ) {
//                        Text(text = "BUY NOW", color = Color.White)
//                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ProductCardPreview(){
    ProductCard(
        quantity = 4,
        onIncrease = { /*TODO*/ },
        onDecrease = { /*TODO*/ },
        imageUrl = "",
        category = "Home",
        rating = "4.5",
        reviews = "1135",
        productName = "Test Product",
        discountedPrice = "799",
        originalPrice = "800",
        onAddClick = { /*TODO*/ }) {

    }
}