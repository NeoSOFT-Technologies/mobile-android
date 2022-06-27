package com.arch.template.ui.feature.resourcedetails

import androidx.activity.viewModels
import com.arch.presentation.viewmodels.resourcedetails.ResourceDetailsViewModel
import com.arch.template.R
import com.arch.template.base.BaseActivity
import com.arch.template.databinding.ActivityResourceDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResourceDetailActivity :
    BaseActivity<ActivityResourceDetailsBinding, ResourceDetailsViewModel>() {

    override val viewModel by viewModels<ResourceDetailsViewModel>()

    override fun getLayoutRes() = R.layout.activity_resource_details

    override fun initViewModel(viewModel: ResourceDetailsViewModel) {

        // Specify the current activity as the lifecycle owner.
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        viewModel.getResourceDetails(getIntExtraOrThrow("id"))
    }


}