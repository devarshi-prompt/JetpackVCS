package com.example.jetpackvcs.di

import android.content.Context
import androidx.room.Room
import com.example.jetpackvcs.db.database.UserDatabase
import com.example.jetpackvcs.db.entities.UserEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,UserDatabase::class.java,"user_database")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(userDatabase: UserDatabase) = userDatabase.userDao()

}