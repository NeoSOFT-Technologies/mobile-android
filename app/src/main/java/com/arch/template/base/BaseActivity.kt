package com.arch.template.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.arch.presentation.viewmodels.base.BaseViewModel
import com.arch.template.R

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    abstract val viewModel: VM

    val binding by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes()) as B
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel(viewModel)

        viewModel.exceptionHandler.bind(
            lifecycleOwner = this,
            activity = this
        )

        // Prepares the permissions controller and binds it to the activity lifecycle.
        viewModel.permissionHandler.bind(
            lifecycle = this.lifecycle,
            fragmentManager = supportFragmentManager
        )
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun initViewModel(viewModel: VM)

    fun replaceFragment(fragment: Fragment, containerId: Int = R.id.fl_container) {
        getBaseTransaction()
            .replace(containerId, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }

    fun addFragment(fragment: Fragment, containerId: Int = R.id.fl_container) {
        getBaseTransaction().add(containerId, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }

    fun getBaseTransaction() =
        supportFragmentManager.beginTransaction()
            // TODO : animation if required
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )

    fun replaceFragmentWithoutBackstack(fragment: Fragment, containerId: Int = R.id.fl_container) {
        getBaseTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    fun removeAndReplaceFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        replaceFragment(fragment)
    }

    /**
     * To get string from Intent, or throw exception if not found
     */
    @Throws(IllegalArgumentException::class)
    fun getStringExtraOrThrow(key: String): String {
        val value = intent.extras?.getString(key)
        require(value != null) { "No data found with key '$key'" }
        return value
    }

    @Throws(IllegalArgumentException::class)
    fun getIntExtraOrThrow(key: String): Int {
        val value = intent.extras?.getInt(key)
        require(value != null) { "No data found with key '$key'" }
        return value
    }

    @Throws(IllegalArgumentException::class)
    fun getBooleanExtraOrThrow(key: String): Boolean {
        val value = intent.extras?.getBoolean(key)
        require(value != null) { "No data found with key '$key'" }
        return value
    }

    @Throws(IllegalArgumentException::class)
    protected inline fun <reified T> getSerializableOrThrow(key: String): T {

        val serializableExtra = intent.getSerializableExtra(key)
            ?: throw IllegalArgumentException("No serialized found with key '$key'")

        if (serializableExtra is T) {
            return serializableExtra
        } else {
            throw IllegalArgumentException("'$key' is not ${T::class.java.simpleName}")
        }
    }

    protected fun clearBackstack() {
        supportFragmentManager.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }


}
