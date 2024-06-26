package com.example.githubuser.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GithubUserDatabase : RoomDatabase() {
    abstract fun githubUserDao(): GithubUserDao

    companion object {
        @Volatile
        private var INSTANCE: GithubUserDatabase? = null

        fun getDatabase(context: Context): GithubUserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GithubUserDatabase::class.java,
                    "github_user"
                ).build()
            }
    }
}