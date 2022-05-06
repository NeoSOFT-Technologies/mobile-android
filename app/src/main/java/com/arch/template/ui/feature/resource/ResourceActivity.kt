package com.arch.template.ui.feature.resource

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.arch.presentation.viewmodels.resources.ResourceViewModel
import com.arch.template.R
import com.arch.template.base.BaseActivity
import com.arch.template.databinding.ActivityResourceBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ResourceActivity : BaseActivity<ActivityResourceBinding, ResourceViewModel>() {

    override val viewModel by viewModels<ResourceViewModel>()

    override fun getLayoutRes() = R.layout.activity_resource

    override fun initViewModel(viewModel: ResourceViewModel) {

        // Specify the current activity as the lifecycle owner.
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.rvResource.adapter = resourceAdapter
        lifecycleScope.launchWhenStarted {
            viewModel.resourcePagingFlow.collect {
                resourceAdapter.submitData(lifecycle, it)
            }
        }
        viewModel.geoLocationTracker.bind(lifecycle, this, supportFragmentManager)
        viewModel.getResourceData()
        binding.tvHeader.setOnClickListener {
            viewModel.tryError()
        }
        binding.fabRequestPermission.setOnClickListener {
            viewModel.requestForGalleryPermission()
        }

        binding.fabRequestLocation.setOnClickListener {
            viewModel.toggleLocationRequest()
        }
    }

    private val resourceAdapter by lazy {
        ResourceDataAdapter()
    }


}