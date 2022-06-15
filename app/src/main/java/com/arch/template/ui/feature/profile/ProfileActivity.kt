package com.arch.template.ui.feature.profile

import androidx.activity.viewModels
import com.arch.presentation.viewmodels.profile.ProfileViewModel
import com.arch.template.R
import com.arch.template.base.BaseActivity
import com.arch.template.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding, ProfileViewModel>() {

    override val viewModel by viewModels<ProfileViewModel>()

    override fun getLayoutRes() = R.layout.activity_profile

    override fun initViewModel(viewModel: ProfileViewModel) {
        binding.viewModel = viewModel

        viewModel.androidMediaPickerController.bind(lifecycle, supportFragmentManager)

        binding.btnCamera.setOnClickListener { viewModel.onCameraPressed() }
        binding.btnGallery.setOnClickListener { viewModel.onGalleryPressed() }
        //viewModel.textState.observe(this) { binding.textView.text = it }
        viewModel.selectedImage.observe(this) { binding.imgPreview.setImageBitmap(it?.platformBitmap) }
    }


}