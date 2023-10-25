package com.example.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.User
import com.example.githubuser.data.UserRespons
import com.example.githubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MainAdapter()
        adapter.setOnItemClickCallBack(object : MainAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: User) {
                navigateToDetailUser(data.login, data.avatar_url)
            }
        })

        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllUserList().observe(this) {
            if (it != null) {
                val temp = arrayListOf<User>()
                it.map {
                    val item = User(it.username, it.avatarUrl, it.username, 0)
                    temp.add(item)
                }
                adapter.setList(temp)
            } else {
                Toast.makeText(this, "There's no data", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter
    }

    private fun navigateToDetailUser(username: String, avatarUrl: String) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, username)
        intent.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, avatarUrl)
        startActivity(intent)
    }
}