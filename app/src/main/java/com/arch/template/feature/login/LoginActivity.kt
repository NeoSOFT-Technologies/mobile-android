package com.arch.template.feature.login

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.arch.presentation.viewmodels.login.LoginViewModel
import com.arch.template.R
import com.arch.template.base.BaseActivity
import com.arch.template.databinding.ActivityLoginBinding
import com.arch.template.feature.resource.ResourceActivity
import com.arch.utils.Status
import com.arch.utils.extensions.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override val viewModel by viewModels<LoginViewModel>()

    override fun getLayoutRes() = R.layout.activity_login

    override fun initViewModel(viewModel: LoginViewModel) {
        binding.viewModel = viewModel
        binding.btnLogin.setOnClickListener {
            val email: String = binding.edtEmail.text.toString()
            val password: String = binding.edtPassword.text.toString()
            viewModel.doLogin(email = email, password = password)
        }
        binding.tvLoginSkip.setOnClickListener {
            goToDashBoard()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.tokenFlow.collect { tokenResource ->
                when (tokenResource.status) {
                    Status.LOADING -> {
                        binding.pbLogin.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.pbLogin.visibility = View.INVISIBLE
                        binding.tvToken.text = tokenResource.data?.token ?: ""
                        showShortToast(message = tokenResource.data?.token ?: "")
                        goToDashBoard()
                    }
                    Status.ERROR -> {
                        binding.pbLogin.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun goToDashBoard() {
        startActivity(
            Intent(
                this@LoginActivity, ResourceActivity::class.java
            )
        )
        finish()
    }
}