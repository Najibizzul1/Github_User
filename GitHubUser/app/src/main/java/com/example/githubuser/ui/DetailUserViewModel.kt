package com.example.githubuser.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.api.RetrofitClient
import com.example.githubuser.data.DetailUserResponse
import com.example.githubuser.data.UserEntity
import com.example.githubuser.data.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)
    private val _isFavorited = MutableLiveData<Boolean>()
    val isFavorited: LiveData<Boolean> = _isFavorited

    val user = MutableLiveData<DetailUserResponse>()

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun setUserDetail(username: String){
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())

                        viewModelScope.launch {
                            _isFavorited.value = isUserFavorited(username) ?: false
                        }
                    }

                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Network Failure", t.message!!)
                }
            })
    }

    fun addUserToFavorite(userEntity: UserEntity) {
        mUserRepository.addUserToFavorite(userEntity)
        _isFavorited.value = true
    }

    fun removeUserFromFavorite(username: String) {
        mUserRepository.removeUserFromFavorite(username)
        _isFavorited.value = false
    }

    suspend fun isUserFavorited(username: String): Boolean {
        return mUserRepository.isUserFavorited(username)
    }
}