package com.example.jetpackvcs.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.jetpackvcs.databinding.FragmentLoginBinding
import com.example.jetpackvcs.ui.auth.login.data.LoginViewModel
import com.example.jetpackvcs.utils.ApiStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel

        return binding.root
    }

    private fun observeStateFlow(view: View){
        lifecycleScope.launch {
            viewModel.loginUiStatesFlow.collect{
                when(it){
                    is ApiStates.onSuccess -> {
                        viewModel._isLoadingFlow.emit(false)
                        Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToUserListFragment())
                    }
                    is ApiStates.onFailure ->{

                    }
                    else -> Unit
                }
            }
        }
    }

}