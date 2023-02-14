package com.example.jetpackvcs.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.jetpackvcs.R
import com.example.jetpackvcs.databinding.ActivityAuthBinding
import com.example.jetpackvcs.utils.Constants
import com.example.jetpackvcs.utils.PreferenceHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    var isUserRegistered: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isUserRegistered = PreferenceHelper(this).getBoolean(Constants.USER_REGISTERED_KEY)
        manageStartDestination()
    }

    private fun manageStartDestination(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        if (!isUserRegistered){
            graph.setStartDestination(R.id.registerFragment)
        }else {
            graph.setStartDestination(R.id.loginFragment)
        }

        val navController = navHostFragment.navController
        navController.setGraph(graph,null)
    }
}