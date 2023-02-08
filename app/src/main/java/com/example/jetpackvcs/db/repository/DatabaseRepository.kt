package com.example.jetpackvcs.db.repository

import com.example.jetpackvcs.db.dao.UserDao
import com.example.jetpackvcs.db.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun insertUser(userEntity: UserEntity){
        userDao.insertUser(userEntity)
    }

    suspend fun updateUser(userEntity: UserEntity){
        userDao.updateUser(userEntity)
    }

    suspend fun deleteUser(userEntity: UserEntity){
        userDao.deleteUser(userEntity)
    }

    suspend fun getUser(id: Int) {
        userDao.getUser(id)
    }

    val allUsers: Flow<List<UserEntity>> = flow {
        emitAll(userDao.getAllUsers())
    }

}