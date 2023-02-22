package com.example.restapiexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.restapiexample.api.NetManager
import com.example.restapiexample.databinding.ActivitySecondBinding
import com.example.restapiexample.repository.MainRepository
import com.example.restapiexample.viewmodel.MainViewModel
import com.example.restapiexample.viewmodel.MyViewModelFactory
import com.squareup.picasso.Picasso

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    private lateinit var viewModel: MainViewModel
    private var getId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getId = intent.getStringExtra("user_id")

        val retrofitService = NetManager.getInstance()
        val mainRepository = MainRepository(retrofitService)
        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository))[MainViewModel::class.java]
        loadData()
        initLivedata()
    }

    private fun initLivedata() {
        viewModel.oneUser.observe(this){
            val user = it.body()
            Picasso.get().load(user?.picture).into(binding.img)
            binding.str.text = user.toString()//all data
        }
    }

    private fun loadData() {
        getId?.let {
            viewModel.getOneUser(it)
        }
    }
}