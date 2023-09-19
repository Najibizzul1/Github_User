package com.example.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubuser.R
import com.example.githubuser.data.DetailUserViewModel
import com.example.githubuser.data.SectionPagerAdapter
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.databinding.ItemUserBinding

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME) ?:""

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailUserViewModel::class.java)

        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this,{
            if (it != null){
                binding.apply{
                    nama.text = it.name
                    Username.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                }
            }
        })

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        binding.apply {
            vp.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(vp)
        }
    }
}