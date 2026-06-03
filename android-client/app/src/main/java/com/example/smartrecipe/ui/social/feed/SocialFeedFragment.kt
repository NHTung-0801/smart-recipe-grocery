package com.example.smartrecipe.ui.social.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.smartrecipe.core.base.BaseFragment
import com.example.smartrecipe.databinding.FragmentSocialFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SocialFeedFragment : BaseFragment<FragmentSocialFeedBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSocialFeedBinding
        get() = FragmentSocialFeedBinding::inflate

    override fun setupViews() {
        // Tạm thời chưa có logic gì ở đây
    }

    override fun observeData() {
        // Tạm thời chưa có dữ liệu để lắng nghe
    }
}