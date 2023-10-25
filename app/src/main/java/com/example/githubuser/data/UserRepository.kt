package com.example.githubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: GithubUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GithubUserDatabase.getDatabase(application)
        mUserDao = db.githubUserDao()
    }

    fun getAllUserFavorited(): LiveData<List<UserEntity>> = mUserDao.getAllUserFavorited()

    fun addUserToFavorite(userEntity: UserEntity) {
        executorService.execute { mUserDao.addUserToFavorite(userEntity) }
    }

    fun removeUserFromFavorite(username: String) {
        executorService.execute { mUserDao.removeUserFromFavorite(username) }
    }

    suspend fun isUserFavorited(username: String): Boolean {
        var fav = false
        withContext(Dispatchers.IO) {
            fav = mUserDao.isUserFavorited(username)
        }
        return fav
    }
}