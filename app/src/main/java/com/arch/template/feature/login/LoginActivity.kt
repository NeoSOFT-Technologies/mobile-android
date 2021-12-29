package com.arch.template.feature.login

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.arch.template.R
import com.arch.template.base.BaseActivity
import com.arch.template.databinding.ActivityLoginBinding
import com.arch.utils.isValidEmail
import com.core.utils.Status
import com.core.utils.extensions.showShortToast

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
            when {
                email.isEmpty() -> {
                    showShortToast(message = this.getString(R.string.error_empty_email))
                }
                !email.isValidEmail() -> {
                    showShortToast(message = this.getString(R.string.error_invalid_email))
                }
                password.isEmpty() -> {
                    showShortToast(message = this.getString(R.string.error_empty_password))
                }
                else -> {
                    viewModel.doLogin(email = email, password = password)
                }
            }
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
                    }
                    Status.ERROR -> {
                        binding.pbLogin.visibility = View.INVISIBLE
                        showShortToast(message = tokenResource.error?.message ?: "")
                    }

                }
            }
        }
    }
}