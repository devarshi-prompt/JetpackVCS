package com.example.jetpackvcs.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackvcs.databinding.ItemUserListBinding
import com.example.jetpackvcs.ui.dashboard.models.User

class UserListAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    class UserListViewHolder(val binding: ItemUserListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.user = user
        }
    }
}