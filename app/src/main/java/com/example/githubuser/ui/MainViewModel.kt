package com.example.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.api.RetrofitClien
import com.example.githubuser.data.User
import com.example.githubuser.data.UserRespons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){

    private val listUsers = MutableLiveData<ArrayList<User>>()

    fun getSearchUsers(): LiveData<ArrayList<User>>{
        return listUsers
    }

    fun setSearchUsers(query: String){
        RetrofitClien.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserRespons>{
                override fun onResponse(
                    call: Call<UserRespons>,
                    response: Response<UserRespons>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserRespons>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }

            })
    }


}

