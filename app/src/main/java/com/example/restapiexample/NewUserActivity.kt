package com.example.restapiexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.restapiexample.api.NetManager
import com.example.restapiexample.databinding.ActivityMainBinding
import com.example.restapiexample.databinding.ActivityNewUserBinding
import com.example.restapiexample.repository.MainRepository
import com.example.restapiexample.viewmodel.MainViewModel
import com.example.restapiexample.viewmodel.MyViewModelFactory
import com.squareup.picasso.Picasso

class NewUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewUserBinding
    private lateinit var viewModel: MainViewModel
    private val IMG_URL = "https://picsum.photos/200/300"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitService = NetManager.getInstance()
        val mainRepository = MainRepository(retrofitService)

        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository))[MainViewModel::class.java]

        Picasso.get().load(IMG_URL).into(binding.img)
        initLiveData()

        var body = initEditText()
        binding.createBtn.setOnClickListener {
            viewModel.newUser(body)
            body = initEditText()//keyingi mail ni generatsiya qilibqo'yadi
        }
    }

    private fun initLiveData() {
        viewModel.progress.observe(this){
            binding.progressBar.isVisible = it
        }
        viewModel.newUserResponse.observe(this){
            val response = it.toString()
            binding.resultText.text = response
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
        }
        viewModel.errorMessage.observe(this){
            Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initEditText(): Map<String, String> {

        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        var email = ('a'..'z').shuffled().subList(0, 6).joinToString("") + "@gmail.com"
        binding.email.setText(email)
        binding.imgUrl.setText(IMG_URL)

        val body = mapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "picture" to IMG_URL,
        )
        return body
    }
}