package com.example.githubuser.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.api.RetrofitClien
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {
    val user = MutableLiveData<DetaiUserResponse>()

    fun setUserDetail(username: String){
        RetrofitClien.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetaiUserResponse>{
                override fun onResponse(
                    call: Call<DetaiUserResponse>,
                    response: Response<DetaiUserResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetaiUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })
    }

    fun getUserDetail(): LiveData<DetaiUserResponse> {
        return user
    }
}