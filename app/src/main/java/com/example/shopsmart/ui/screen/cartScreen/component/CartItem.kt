package com.example.shopsmart.ui.screen.cartScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.shopsmart.data.local.entites.CartEntity

@Composable
fun CartItem(
    cartEntity: CartEntity,
    onQuantityChange: (Int) -> Unit,
    onProductClick:()->Unit,
    onRemoveItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onProductClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Image
        Image(
            painter = rememberAsyncImagePainter(cartEntity.imageUrl),
            contentDescription = cartEntity.name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Product Details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = cartEntity.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)) // Rounded corners for the entire row
                    .padding(8.dp) // Adding padding around the row
            ) {
                // Decrease button
                IconButton(
                    onClick = {  if (cartEntity.quantity > 0) onQuantityChange(cartEntity.quantity - 1) }, // Ensure onDecrease is invoked properly
                    modifier = Modifier
                        .size(20.dp) // Consistent button size
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
                    text = cartEntity.quantity.toString(),
                    modifier = Modifier.padding(horizontal = 12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                // Increase button
                IconButton(
                    onClick = { onQuantityChange(cartEntity.quantity + 1) }, // Ensure onIncrease is invoked properly
                    modifier = Modifier
                        .size(20.dp) // Consistent button size
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

            // Quantity Selector

        }

        Spacer(modifier = Modifier.width(16.dp))

        // Product Price and Remove Button
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "₹ ${cartEntity.discountedPrice}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
              //  color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            IconButton(
                onClick = { onRemoveItem() },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove Item",
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview
@Composable
fun CartItemPreview(){
    CartItem(CartEntity(1,"Testing Name","",499.0,599.0,4),{},{},{})
}