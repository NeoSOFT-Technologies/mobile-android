package com.arch.template.ui.feature.resource

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.arch.presentation.model.ResourceDataPresentation
import com.arch.presentation.viewmodels.resources.ResourceViewModel
import com.arch.template.R
import com.arch.template.base.BaseActivity
import com.arch.template.databinding.ActivityResourceBinding
import com.arch.template.ui.feature.geolocation.GeolocationActivity
import com.arch.template.ui.feature.profile.ProfileActivity
import com.arch.template.ui.feature.resourcedetails.ResourceDetailActivity
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

        viewModel.getResourceData()
        binding.tvHeader.setOnClickListener {
            viewModel.tryError()
        }
        binding.fabRequestPermission.setOnClickListener {
            viewModel.requestForGalleryPermission()
        }


        binding.ivFullCircle.setOnClickListener {
            goToProfile()
        }

        binding.ivLocation.setOnClickListener {
            goToGeoLocation()
        }
    }

    private fun goToProfile() {
        startActivity(
            Intent(
                this@ResourceActivity, ProfileActivity::class.java
            )
        )
    }

    private fun goToGeoLocation() {
        startActivity(
            Intent(
                this@ResourceActivity, GeolocationActivity::class.java
            )
        )
    }

    private fun goToResourceDetails(id: Int) {
        startActivity(
            Intent(
                this@ResourceActivity, ResourceDetailActivity::class.java
            ).apply {
                putExtra("id", id)
            }
        )
    }

    private val resourceAdapter by lazy {
        ResourceDataAdapter(::onSelect)
    }

    private fun onSelect(item: ResourceDataPresentation) {
        item.id?.let { goToResourceDetails(it) }
    }


}