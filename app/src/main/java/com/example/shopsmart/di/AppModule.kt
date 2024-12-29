package com.example.shopsmart.di
//

import android.content.Context
import androidx.room.Room
import com.example.shopsmart.data.local.database.CartDatabase
import com.example.shopsmart.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent ::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): CartDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CartDatabase::class.java,
            "CartDataBase"
        ).build()
    }
    @Provides
    @Singleton
    fun provideRepository(db :CartDatabase): Repository {
        return Repository(db)
    }

}