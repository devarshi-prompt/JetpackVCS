package com.example.jetpackvcs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.jetpackvcs.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manageStartDestination()
    }
    override fun onBackPressed() {
        finishAffinity()
    }

    private fun manageStartDestination(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        if (!Constants.isUserRegistered){
            graph.setStartDestination(R.id.registerFragment)
        }else {
            graph.setStartDestination(R.id.loginFragment)
        }

        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
    }
}