package com.example.restapiexample

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restapiexample.adapter.UserAdapter
import com.example.restapiexample.api.NetManager
import com.example.restapiexample.databinding.ActivityMainBinding
import com.example.restapiexample.repository.MainRepository
import com.example.restapiexample.viewmodel.MainViewModel
import com.example.restapiexample.viewmodel.MyViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapterUser by lazy { UserAdapter() }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Doc: https://dummyapi.io/docs/user*/

        val retrofitService = NetManager.getInstance()
        val mainRepository = MainRepository(retrofitService)

        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository))[MainViewModel::class.java]

        initLiveData()
        initRec()
        loadData()

        binding.swipeRef.setOnRefreshListener {
            loadData()
        }
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, NewUserActivity::class.java))
        }

    }

    private fun initRec() {
        binding.rec.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterUser
        }
        adapterUser.setOnItemClickListener {
            Log.d("dsfghjk", it)
            viewModel.deleteUser(it)
        }
    }

    private fun loadData() {
        viewModel.getAllUser()
    }

    private fun initLiveData() {
        viewModel.userList.observe(this){
            adapterUser.baseList = it.body()?.data ?: emptyList()
        }
        viewModel.errorMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.deleteUser.observe(this){
            Toast.makeText(this, "${it.body()}" , Toast.LENGTH_SHORT).show()
            loadData()
        }
        viewModel.progress.observe(this){
            binding.swipeRef.isRefreshing = it
        }
    }
}