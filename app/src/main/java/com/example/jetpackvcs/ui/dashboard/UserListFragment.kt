package com.example.jetpackvcs.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.jetpackvcs.databinding.FragmentUserListBinding
import com.example.jetpackvcs.ui.dashboard.data.UserListViewModel
import com.example.jetpackvcs.utils.ApiStates
import com.example.jetpackvcs.utils.Constants.Companion.toast
import com.example.jetpackvcs.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private var binding: FragmentUserListBinding by autoCleared()
    val viewModel: UserListViewModel by viewModels()
    //lateinit var adapter: UserListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentUserListBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel
        observeStatesFlow()
        return binding.root
    }

    private fun observeStatesFlow(){
        viewModel.getAllUsers()
        lifecycleScope.launch {
            viewModel.userListUiStatesFlow.collect{
                when(it){
                    is ApiStates.OnSuccess ->{
                        viewModel.isLoading.value = false
                        Log.d("UserResponseSuccess", "observeStatesFlow: ${it.response.body()?.data}")
                    }
                    is ApiStates.OnFailure ->{
                        viewModel.isLoading.value = false
                        toast(requireContext(),it.error)
                    }
                    is ApiStates.IsLoading ->{
                        viewModel.isLoading.value = true
                    }
                }
            }
        }
    }


}