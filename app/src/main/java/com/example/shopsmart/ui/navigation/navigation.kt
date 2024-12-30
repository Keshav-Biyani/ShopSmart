package com.example.shopsmart.ui.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopsmart.ui.viewmodel.SharedViewModel
import com.example.shopsmart.ui.screen.cartScreen.CartScreenStateful
import com.example.shopsmart.ui.screen.productDetailScreen.ProductDetailScreen

import com.example.shopsmart.ui.screen.productListScreen.ProductListScreenStateFull



@Composable
fun Navigation(viewModel: SharedViewModel = hiltViewModel()){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppNavigation.ProductScreen.route ){
        composable(AppNavigation.ProductScreen.route) {
            ProductListScreenStateFull(viewModel, onProductClick = {productId->
                Log.e("ProductId",productId.toString())
                navController.navigate("${AppNavigation.ProductDetailScreen.route}/${productId}")

            }){
                navController.navigate(AppNavigation.CartScreen.route)
            }

        }
        composable(AppNavigation.CartScreen.route) {
            CartScreenStateful(viewModel , onStartShopping = {
                navController.navigate(AppNavigation.ProductScreen.route)
            },{ productId->
                    Log.e("ProductId",productId.toString())
                    navController.navigate("${AppNavigation.ProductDetailScreen.route}/${productId}")


            }){
                navController.popBackStack()
            }
        }
        composable("${AppNavigation.ProductDetailScreen.route}/{productId}") {backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt()
            val productList by viewModel.products.collectAsState()
            val cartItemCount by viewModel.cartItemCount.collectAsState()
            val selectedProduct = productList.firstOrNull { it.id == productId }
if(selectedProduct != null) {
    ProductDetailScreen(product = selectedProduct,selectedProduct.quantity,{  productId->        viewModel.updateProductQuantity(productId, selectedProduct.quantity + 1)},{ productId->        viewModel.updateProductQuantity(productId, selectedProduct.quantity - 1)},cartItemCount, onBackClick = { navController.popBackStack() }, onAddToCart = {         viewModel.updateProductQuantity(selectedProduct.id, selectedProduct.quantity + 1)},{
        navController.navigate(AppNavigation.CartScreen.route)
    }) {

    }
}else{
        Text("Product not found not found.")
    }
        }
    }


}