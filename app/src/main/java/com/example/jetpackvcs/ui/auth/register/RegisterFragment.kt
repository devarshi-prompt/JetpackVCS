package com.example.jetpackvcs.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.jetpackvcs.databinding.FragmentRegisterBinding
import com.example.jetpackvcs.ui.auth.auth_data.AuthViewModel
import com.example.jetpackvcs.utils.*
import com.example.jetpackvcs.utils.Constants.Companion.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var binding: FragmentRegisterBinding by autoCleared()
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel
        viewModel.isRegister.value = true
        observeValidation()
        return binding.root
    }

    private fun observeValidation(){
        lifecycleScope.launch{
            viewModel.registerValidationFlow.collect{
                when(it){
                    is Validation.ValidationSuccess -> {
                        observerRegisterResponse()
                        PreferenceHelper(requireContext()).putBoolean(Constants.USER_REGISTERED_KEY,true)
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                    }
                    is Validation.ValidationFailed -> {
                        toast(requireContext(),it.message).show()
                    }
                    is Validation.Empty -> Unit
                }
            }
        }
    }
    private fun observerRegisterResponse(){
        lifecycleScope.launch {
            viewModel.registerUiStatesFlow.collect{
                when(it){
                    is ApiStates.OnSuccess -> {
                        viewModel._isLoadingFlow.value = false
                        toast(requireContext(),"Registration Success").show()
                    }
                    is ApiStates.OnFailure -> {
                        viewModel._isLoadingFlow.value = false
                        toast(requireContext(),it.error).show()
                    }
                    is ApiStates.IsLoading -> {
                        viewModel._isLoadingFlow.value = true
                    }
                }
            }
        }
    }
}