package com.example.jetpackvcs.ui.auth.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.jetpackvcs.BR
import com.example.jetpackvcs.R
import com.example.jetpackvcs.databinding.FragmentLoginBinding
import com.example.jetpackvcs.ui.auth.viewmodels.LoginViewModel
import com.example.jetpackvcs.utils.Constants.Companion.isValidEmail
import kotlinx.coroutines.launch

class LoginFragment : Fragment(),View.OnClickListener {

    lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        binding.setVariable(BR.onButtonClick,this)

        binding.editTextLoginEmail.addTextChangedListener(emailTextWatcher)
        binding.editTextLoginPassword.addTextChangedListener(passwordTextWatcher)

        showProgressBar()

        return binding.root
    }

    private fun loginUser(loginEmail: String, password: String){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.loginUser(loginEmail, password)
            }
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonLogin -> {
                if (binding.editTextLoginEmail.text.toString().isNotEmpty() && binding.editTextLoginPassword.text.toString().isNotEmpty()){
                    if (binding.editTextLoginEmail.text.isValidEmail()){
                        loginUser(binding.editTextLoginEmail.text.toString(),binding.editTextLoginPassword.text.toString())
                    } else{
                        binding.textInputLayoutLoginEmail.isErrorEnabled = true
                        binding.textInputLayoutLoginEmail.error = "Invalid Email"
                    }
                } else if(binding.editTextLoginEmail.text.toString().isEmpty() && binding.editTextLoginPassword.text.toString().isNotEmpty()){
                    binding.textInputLayoutLoginEmail.isErrorEnabled = true
                    binding.textInputLayoutLoginEmail.error = "Please enter an email"
                } else if (binding.editTextLoginEmail.text.toString().isNotEmpty() && binding.editTextLoginPassword.text.toString().isEmpty()){
                    binding.textInputLayoutPassword.isErrorEnabled = true
                    binding.textInputLayoutPassword.error = "Please enter a password"
                } else{
                    binding.textInputLayoutLoginEmail.isErrorEnabled = true
                    binding.textInputLayoutLoginEmail.error = "Please enter an email"
                    binding.textInputLayoutPassword.isErrorEnabled = true
                    binding.textInputLayoutPassword.error = "Please enter a password"
                }
            }
        }
    }

    private val emailTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.textInputLayoutLoginEmail.isErrorEnabled = false
            binding.textInputLayoutLoginEmail.error = ""
        }

        override fun afterTextChanged(editText: Editable?) {

        }
    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.textInputLayoutPassword.isErrorEnabled = false
            binding.textInputLayoutPassword.error = ""
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }

    private fun showProgressBar(){
        lifecycleScope.launch {
            viewModel.isLoadingFlow.collect{
                if (it){
                    binding.loginProgressBar.visibility = View.VISIBLE
                } else{
                    binding.loginProgressBar.visibility = View.GONE
                }
            }
        }
    }
}