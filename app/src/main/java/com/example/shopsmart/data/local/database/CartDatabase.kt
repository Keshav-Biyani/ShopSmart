package com.example.shopsmart.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shopsmart.data.local.dao.CartDao
import com.example.shopsmart.data.local.entites.CartEntity

@Database(
    entities = [CartEntity ::class],
    version = 1
)
abstract class CartDatabase : RoomDatabase(){
    abstract  fun cartDao() : CartDao
}