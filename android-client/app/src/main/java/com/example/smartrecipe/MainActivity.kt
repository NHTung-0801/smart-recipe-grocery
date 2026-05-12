package com.example.smartrecipe

import android.view.LayoutInflater
import com.example.smartrecipe.core.base.BaseActivity
import com.example.smartrecipe.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

}