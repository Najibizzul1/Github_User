package com.example.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.User
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val themeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(application)
        )[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) { isDarkMode: Boolean ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        adapter = MainAdapter()
        adapter.setOnItemClickCallBack(object : MainAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: User) {
                navigateToDetailUser(data.login, data.avatar_url)
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        setupRecyclerView()
        setupSearchButton()

        viewModel.getSearchUsers().observe(this, { users ->
            users?.let {
                adapter.setList(it)
                showLoading(false)
            }
        })

        viewModel.setSearchUsers("all")

        binding.btnFav.setOnClickListener {
            Intent(this, FavoriteActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnTheme.setOnClickListener {
            Intent(this, ThemeActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter
    }

    private fun setupSearchButton() {
        binding.btnSearch.setOnClickListener { searchUser() }

        binding.etQuery.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUser()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun searchUser() {
        val query = binding.etQuery.text.toString()
        if (query.isEmpty()) return
        showLoading(true)
        viewModel.setSearchUsers(query)
    }

    private fun showLoading(state: Boolean) {
        binding.PB.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun navigateToDetailUser(username: String, avatarUrl: String) {
        val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USERNAME, username)
        intent.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, avatarUrl)
        startActivity(intent)
    }
}
