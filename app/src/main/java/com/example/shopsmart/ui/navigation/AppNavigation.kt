package com.example.shopsmart.ui.navigation


sealed class AppNavigation(val route : String) {
    data object ProductScreen: AppNavigation("product_screen")
    data object ProductDetailScreen : AppNavigation("product_detail_screen")
    data object  CartScreen : AppNavigation("cart_screen")
}
