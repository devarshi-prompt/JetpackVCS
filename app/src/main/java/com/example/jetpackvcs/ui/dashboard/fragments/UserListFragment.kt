package com.example.jetpackvcs.ui.dashboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.jetpackvcs.R
import com.example.jetpackvcs.databinding.FragmentUserListBinding

class UserListFragment : Fragment() {

    lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_list,container,false)

        return binding.root
    }
}