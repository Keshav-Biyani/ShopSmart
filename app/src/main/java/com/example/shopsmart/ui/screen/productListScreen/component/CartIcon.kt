package com.example.shopsmart.ui.screen.productListScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CartIcon(quantity: Int,onNavigation: () ->Unit) {
    Box {
        IconButton(onClick = { /* Navigate to Cart */
            onNavigation()
        }) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart"
            )
        }
        if (quantity > 0) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .background(Color.Red, shape = CircleShape)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$quantity",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
@Preview
@Composable
fun CardIcon(){
    CartIcon(quantity = 5){}
}