package com.example.githubuser.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GithubUserDao {
    @Query("SELECT * FROM user")
    fun getAllUserFavorited(): LiveData<List<UserEntity>>

    @Insert
    fun addUserToFavorite(userEntity: UserEntity)

    @Query("DELETE FROM user WHERE username = :username")
    fun removeUserFromFavorite(username: String)

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    suspend fun isUserFavorited(username: String): Boolean
}