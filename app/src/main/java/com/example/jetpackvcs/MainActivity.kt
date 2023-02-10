package com.example.jetpackvcs

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.jetpackvcs.databinding.ActivityMainBinding
import com.example.jetpackvcs.ui.splash.fragments.SplashFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var isUserRegistered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        isUserRegistered = getSharedPreferences("isUserRegistered", MODE_PRIVATE).getBoolean("userRegistered",false)

        manageAuthFragment()
    }
    override fun onBackPressed() {
        finishAffinity()
    }

    private fun manageAuthFragment(){
        Handler(Looper.getMainLooper()).postDelayed({
            val action = if (!isUserRegistered){
                SplashFragmentDirections.actionSplashFragmentToRegisterFragment()
            } else{
                SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            }
            Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(action)
        },2000)
    }
}