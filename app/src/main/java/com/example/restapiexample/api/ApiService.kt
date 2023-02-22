package com.example.restapiexample.api

import com.example.restapiexample.models.User
import com.example.restapiexample.models.UserModel
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @Headers("app-id:63f4644d95e5a789f3274617")
    @GET("user")
    suspend fun getAllUser() : Response<UserModel>

    @Headers("app-id:63f4644d95e5a789f3274617")
    @GET("user/{inputId}")//get post method
    suspend fun getOneUser(
        @Path("inputId") userId: String
    ) : Response<User>


    @POST("user/create")
    suspend fun newUser(
        @Body user: Map<String, String>
    ) : Response<Any>/* Map o'rniga User model ga o'rab jo'natiw mumkin lekin User modelda email yo'q, Map bilan bu yo'lini korsatibketvoman*/


    @Headers("app-id:63f4644d95e5a789f3274617")
    @PUT("user/{inputId2}")
    suspend fun updateUser(
        @Path("inputId2") userId: String,
        @Body user: User
    ): Response<User>

    @Headers("app-id:63f4644d95e5a789f3274617")
    @DELETE("user/{inputId3}")
    suspend fun deleteUser(
        @Path("inputId3") userId: String
    ): Response<Any>/* Any o'rniga response -> ("id": "user id") wune dodelga o'rab olibkeliw mumkin */



    @GET("user/{inputId}")//get method with custom header and path
    suspend fun getOneUserCustomHeader(
        @HeaderMap headers: Map<String, String>,
        @Path("inputId") number: Int
    ) : Response<User>

    @GET("user")//get user with Query
    suspend fun getCustomOneUser(
        @Query("id") userId: Int,
        @Query("_name") userName: String,
        @Query("_age") userAge: Int,
    ) : Response<User>







}