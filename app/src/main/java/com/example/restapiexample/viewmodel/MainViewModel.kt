package com.example.restapiexample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapiexample.models.User
import com.example.restapiexample.models.UserModel
import com.example.restapiexample.repository.MainRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()

    val userList = MutableLiveData<Response<UserModel>>()
    val oneUser = MutableLiveData<Response<User>>()
    val deleteUser = MutableLiveData<Response<Any>>()
    val progress = MutableLiveData<Boolean>()
    val newUserResponse = MutableLiveData<Response<Any>>()

    fun getAllUser() {
        progress.value = true
        viewModelScope.launch {
            val response = mainRepository.getAllUser()
            if (response.isSuccessful) {
                userList.value = response
            } else {
                errorMessage.value = response.message()
            }
            progress.value = false
        }
    }
    fun getOneUser(userId: String){
        progress.value = true
        viewModelScope.launch {
            val response = mainRepository.getOneUser(userId)
            if (response.isSuccessful) {
                oneUser.value = response
            } else {
                errorMessage.value = response.message()
            }
            progress.value = false
        }
    }
    fun deleteUser(userId: String){
        progress.value = true
        viewModelScope.launch {
            val response = mainRepository.deleteUser(userId)
            if (response.isSuccessful) {
                deleteUser.value = response
            } else {
                errorMessage.value = response.message()
            }
            progress.value = false
        }
    }
    fun newUser(newUer: Map<String, String>){
        progress.value = true
        viewModelScope.launch {
            val response = mainRepository.newUser(newUer)
            newUserResponse.value = response
            progress.value = false
        }
    }
    fun updateUser(userId: String, body: User){
        progress.value = true
        viewModelScope.launch {
            val response = mainRepository.updateUser(userId, body)
            oneUser.value = response
            progress.value = false
        }
    }


}