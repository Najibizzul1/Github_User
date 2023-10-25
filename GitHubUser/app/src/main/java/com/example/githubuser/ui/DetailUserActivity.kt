package com.example.githubuser.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.R
import com.example.githubuser.data.UserEntity
import com.example.githubuser.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setColor()

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: ""
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL) ?: ""
        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, factory)[DetailUserViewModel::class.java]
        viewModel.setUserDetail(username)
        showLoading(true)

        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    nama.text = it.name
                    Username.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                    showLoading(false)

                }
            }
        })

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            vp.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(vp)
        }

        viewModel.isFavorited.observe(this) {
            if (it) {
                binding.floatingActionButton.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_border)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            if (viewModel.isFavorited.value == true) {
                viewModel.removeUserFromFavorite(username)
                Toast.makeText(this, "Removed from favorite", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addUserToFavorite(
                    UserEntity(
                        username = username,
                        avatarUrl = avatarUrl
                    )
                )
                Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun showLoading(state: Boolean) {
        binding.PB.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setColor() {
        val themeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(application)
        )[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) { isDarkMode: Boolean ->
            if (isDarkMode) {
                TextViewCompat.setCompoundDrawableTintList(binding.tvFollowers, ColorStateList.valueOf(ContextCompat.getColor(baseContext, R.color.white)))
//                binding.tvFollowers.compoundDrawables[0]?.setTint(resources.getColor(R.color.white))
            }
        }
    }
}