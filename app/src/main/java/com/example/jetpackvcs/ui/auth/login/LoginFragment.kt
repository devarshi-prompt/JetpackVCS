package com.example.jetpackvcs.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.jetpackvcs.databinding.FragmentLoginBinding
import com.example.jetpackvcs.ui.auth.auth_data.AuthViewModel
import com.example.jetpackvcs.ui.dashboard.MainActivity
import com.example.jetpackvcs.utils.ApiStates
import com.example.jetpackvcs.utils.Constants.Companion.toast
import com.example.jetpackvcs.utils.Validation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel
        viewModel.isRegister.value = false
        observeValidation()
        return binding.root
    }
    private fun observeValidation(){
        lifecycleScope.launch{
            viewModel.loginValidationFlow.collect{
                when(it){
                    is Validation.ValidationSuccess -> {
                        observeStateFlow()
                        startActivity(Intent(requireContext(),MainActivity::class.java))
                    }
                    is Validation.ValidationFailed -> {
                        toast(requireContext(),it.message).show()
                    }
                    else -> Unit
                }
            }
        }
    }
    private fun observeStateFlow(){
        lifecycleScope.launch {
            viewModel.loginUiStatesFlow.collect{
                when(it){
                    is ApiStates.OnSuccess -> {
                        viewModel._isLoadingFlow.value = false
                        toast(requireContext(),"Login Success").show()
                    }
                    is ApiStates.OnFailure ->{
                        viewModel._isLoadingFlow.value = false
                        toast(requireContext(),it.error).show()
                    }
                    is ApiStates.IsLoading ->{
                        viewModel._isLoadingFlow.value = true
                    }
                }
            }
        }
    }
}