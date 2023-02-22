package com.example.restapiexample.models

data class User(
    val firstName: String,
    val lastName: String,
    val picture: String
): java.io.Serializable{
    val id: String? = null
}