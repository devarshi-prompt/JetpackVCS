package com.example.jetpackvcs.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.jetpackvcs.R
import com.example.jetpackvcs.databinding.FragmentUserListBinding
import com.example.jetpackvcs.ui.dashboard.data.UserListViewModel
import com.example.jetpackvcs.ui.dashboard.models.User
import com.example.jetpackvcs.utils.ApiStates
import com.example.jetpackvcs.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.net.UnknownServiceException

@AndroidEntryPoint
class UserListFragment : Fragment() {

    var binding: FragmentUserListBinding by autoCleared()
    val viewModel: UserListViewModel by viewModels()
    lateinit var adapter: UserListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentUserListBinding.inflate(layoutInflater)

        return binding.root
    }

    fun observeStatesFlow(){
        viewModel.getAllUsers()
        lifecycleScope.launch {
            viewModel.userListUiStatesFlow.collect{
                when(it){
                    is ApiStates.OnSuccess -> {
                        adapter = UserListAdapter(it.response.body() as List<User>)
                    }
                }
            }
        }
    }


}