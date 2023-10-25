package com.example.githubuser.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.UserEntity
import com.example.githubuser.data.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mUserRepository: UserRepository = UserRepository(application)

    init {
        getAllUserList()
    }

    fun getAllUserList(): LiveData<List<UserEntity>> {
        return mUserRepository.getAllUserFavorited()
    }

}