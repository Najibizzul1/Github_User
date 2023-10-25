package com.example.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.api.RetrofitClient
import com.example.githubuser.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setlistFollowing(username: String){
        RetrofitClient.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<List<User>>{
                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
                ) {
                    if (response.isSuccessful){
                        listFollowing.postValue(response.body() as ArrayList<User>?)
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.d("Network Failure", t.message!!)
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<User>>{
        return listFollowing
    }
}

