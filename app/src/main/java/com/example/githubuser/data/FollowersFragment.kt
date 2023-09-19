package com.example.githubuser.data

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.githubuser.R
import com.example.githubuser.databinding.FragmentFollowBinding

class FollowersFragment: Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}