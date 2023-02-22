package com.example.restapiexample.repository

import com.example.restapiexample.api.ApiService
import com.example.restapiexample.models.User

class MainRepository constructor(private val retrofitService: ApiService) {

    suspend fun getAllUser() = retrofitService.getAllUser()

    suspend fun getOneUser(userId: String) = retrofitService.getOneUser(userId)

    suspend fun deleteUser(userId: String) = retrofitService.deleteUser(userId)

    suspend fun newUser(newUser: Map<String, String>) = retrofitService.newUser(newUser)

    suspend fun updateUser(userId: String, body: User) = retrofitService.updateUser(userId, body)

}
