package com.example.restapiexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.restapiexample.api.NetManager
import com.example.restapiexample.databinding.ActivityNewUserBinding
import com.example.restapiexample.databinding.ActivityUpdateBinding
import com.example.restapiexample.models.User
import com.example.restapiexample.repository.MainRepository
import com.example.restapiexample.viewmodel.MainViewModel
import com.example.restapiexample.viewmodel.MyViewModelFactory
import com.squareup.picasso.Picasso

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var getIntent: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getIntent = intent.getSerializableExtra("update_key") as User
        val retrofitService = NetManager.getInstance()
        val mainRepository = MainRepository(retrofitService)

        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository))[MainViewModel::class.java]

        initLiveData()
        initEditText()
        binding.createBtn.setOnClickListener {
//            viewModel.updateUser(body.id!!, body)
            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastName.text.toString()
            val imgUrl = binding.imgUrl.text.toString()

            val body = User(firstName = firstName, lastName = lastName, picture = imgUrl)
            viewModel.updateUser(getIntent.id!!, body)
            finish()
        }
    }

    private fun initLiveData() {
        viewModel.progress.observe(this){
            binding.progressBar.isVisible = it
        }
        viewModel.oneUser.observe(this){
            val response = it.body().toString()
            binding.resultText.text = response
            Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
        }
        viewModel.errorMessage.observe(this){
            Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
        }
    }
    private fun initEditText() {

        binding.firstName.setText(getIntent.firstName)
        binding.lastName.setText(getIntent.lastName)
        binding.imgUrl.setText(getIntent.picture)
        Picasso.get().load(getIntent.picture).into(binding.img)
    }
}