package com.arch.template.ui.feature.geolocation

import androidx.activity.viewModels
import com.arch.presentation.viewmodels.geolocation.GeoLocationViewModel
import com.arch.template.R
import com.arch.template.base.BaseActivity
import com.arch.template.databinding.ActivityGeolocationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeolocationActivity : BaseActivity<ActivityGeolocationBinding, GeoLocationViewModel>() {

    override val viewModel by viewModels<GeoLocationViewModel>()

    override fun getLayoutRes() = R.layout.activity_geolocation

    override fun initViewModel(viewModel: GeoLocationViewModel) {
        // Specify the current activity as the lifecycle owner.
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        viewModel.geoLocationTracker.bind(lifecycle, this, supportFragmentManager)

        binding.fabRequestLocation.setOnClickListener {
            viewModel.toggleLocationRequest()
            if(viewModel.geoLocationTracker.isStarted){
                binding.fabRequestLocation.setImageResource(android.R.drawable.ic_media_pause)
            }else{
                binding.fabRequestLocation.setImageResource(android.R.drawable.ic_media_play)
            }
        }

    }


}