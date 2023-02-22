package com.example.restapiexample.models

data class UserModel(
    val data: List<User>,
    val limit: Int,
    val page: Int,
    val total: Int
)