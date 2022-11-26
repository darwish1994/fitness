package com.app.fitness.presenter.details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.app.fitness.R
import com.app.fitness.common.extention.applyMapCamera
import com.app.fitness.common.extention.getDistanceCovered
import com.app.fitness.common.extention.timerFormat
import com.app.fitness.common.extention.viewBinding
import com.app.fitness.databinding.FragmentDetailsBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details), OnMapReadyCallback {

    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val viewModel by viewModels<DetailsViewModel>()
    private val args by navArgs<DetailsFragmentArgs>()

    companion object {
        const val zoom = 18.0f
        const val fontSize=15f
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        lifecycleScope.launch {
            val session = viewModel.getSession(args.sessionId)
            binding.tvDistance.text = session.session.steps?.getDistanceCovered()
            binding.tvSteps.text = session.session.steps?.toString()
            binding.tvTimer.text = session.session.duration?.timerFormat()

            session.locations.firstOrNull()?.let {
                map.applyMapCamera(LatLng(it.latitude, it.longitude), zoom)
            }
            map.addPolyline(PolylineOptions().addAll(session.locations.map {
                LatLng(
                    it.latitude,
                    it.longitude
                )
            }).color(Color.RED).width(fontSize))


        }

    }


}