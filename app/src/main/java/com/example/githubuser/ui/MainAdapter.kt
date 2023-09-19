package com.example.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.data.User
import com.example.githubuser.databinding.ItemUserBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()

    fun setList(users: ArrayList<User>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
      fun bind(user: User){
          binding.apply {
              Glide.with(itemView)
                  .load(user.avatar_url)
                  .transition(DrawableTransitionOptions.withCrossFade())
                  .centerCrop()
                  .into(ivUser)
              tvUser.text  = user.login
          }

      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }
}