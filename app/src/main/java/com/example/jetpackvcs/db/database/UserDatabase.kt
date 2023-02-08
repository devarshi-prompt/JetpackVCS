package com.example.jetpackvcs.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackvcs.db.dao.UserDao
import com.example.jetpackvcs.db.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
}