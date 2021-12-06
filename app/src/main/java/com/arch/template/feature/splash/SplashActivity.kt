package com.arch.template.feature.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arch.template.R
import androidx.activity.viewModels
import com.arch.template.base.BaseActivity
import com.arch.template.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override val viewModel by viewModels<SplashViewModel>()

    override fun getLayoutRes() = R.layout.activity_splash

    override fun initViewModel(viewModel: SplashViewModel) {
        binding.viewModel = viewModel

    }


}