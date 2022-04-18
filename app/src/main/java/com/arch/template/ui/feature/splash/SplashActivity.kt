package com.arch.template.ui.feature.splash

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.arch.presentation.viewmodels.splash.SplashViewModel
import com.arch.template.R
import com.arch.template.base.BaseActivity
import com.arch.template.databinding.ActivitySplashBinding
import com.arch.template.ui.feature.login.LoginActivity
import com.arch.template.utils.MyAppLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override val viewModel by viewModels<SplashViewModel>()

    override fun getLayoutRes() = R.layout.activity_splash

    override fun initViewModel(viewModel: SplashViewModel) {
        binding.viewModel = viewModel

        lifecycleScope.launchWhenStarted {
            viewModel.navigationFlow.collect { navigation ->
                MyAppLogger.d("navigation is $navigation")
                if (navigation) {
                    startActivity(
                        Intent(
                            this@SplashActivity, LoginActivity::class.java
                        )
                    )
                    finish()
                }
            }
        }
    }


}