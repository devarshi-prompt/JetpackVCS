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
import com.example.jetpackvcs.databinding.FragmentRegisterBinding
import com.example.jetpackvcs.ui.auth.viewmodels.RegisterViewModel
import com.example.jetpackvcs.utils.Constants
import com.example.jetpackvcs.utils.Constants.Companion.isValidEmail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentRegisterBinding
    val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.setVariable(BR.onButtonClick,this)

        binding.editTextRegistrationEmail.addTextChangedListener(emailTextWatcher)
        binding.editTextPassword.addTextChangedListener(passwordTextWatcher)
        showProgressBar()


        return binding.root
    }

    private fun registerUser(registrationEmail: String, password: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerUser(registrationEmail, password)
            }
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonRegister -> {
                if (binding.editTextRegistrationEmail.text.toString().isNotEmpty() && binding.editTextPassword.text.toString().isNotEmpty()){
                    if (binding.editTextRegistrationEmail.text.isValidEmail()){
                        registerUser(binding.editTextRegistrationEmail.text.toString(),binding.editTextPassword.text.toString())
                    } else{
                        binding.textInputLayoutRegistrationEmail.isErrorEnabled = true
                        binding.textInputLayoutRegistrationEmail.error = "Invalid Email"
                    }
                } else if(binding.editTextRegistrationEmail.text.toString().isEmpty() && binding.editTextPassword.text.toString().isNotEmpty()){
                    binding.textInputLayoutRegistrationEmail.isErrorEnabled = true
                    binding.textInputLayoutRegistrationEmail.error = "Please enter an email"
                } else if (binding.editTextRegistrationEmail.text.toString().isNotEmpty() && binding.editTextPassword.text.toString().isEmpty()){
                    binding.textInputLayoutRegistrationPassword.isErrorEnabled = true
                    binding.textInputLayoutRegistrationPassword.error = "Please enter a password"
                } else{
                    binding.textInputLayoutRegistrationEmail.isErrorEnabled = true
                    binding.textInputLayoutRegistrationEmail.error = "Please enter an email"
                    binding.textInputLayoutRegistrationPassword.isErrorEnabled = true
                    binding.textInputLayoutRegistrationPassword.error = "Please enter a password"
                }
            }
        }
    }

    private val emailTextWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.textInputLayoutRegistrationEmail.isErrorEnabled = false
            binding.textInputLayoutRegistrationEmail.error = ""
        }

        override fun afterTextChanged(editText: Editable?) {

        }
    }

    private val passwordTextWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.textInputLayoutRegistrationPassword.isErrorEnabled = false
            binding.textInputLayoutRegistrationPassword.error = ""
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }

    private fun showProgressBar(){
        lifecycleScope.launch {
            viewModel.isLoadingFlow.collect{
                if (it){
                    binding.registerProgressBar.visibility = View.VISIBLE
                } else{
                    binding.registerProgressBar.visibility = View.GONE
                }
            }
        }
    }
}