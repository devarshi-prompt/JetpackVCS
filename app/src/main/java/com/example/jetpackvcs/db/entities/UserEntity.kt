package com.example.jetpackvcs.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @ColumnInfo(name = "user")
    val userName: String="",
    @ColumnInfo(name = "email")
    val emailId: String="")
