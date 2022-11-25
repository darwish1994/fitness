package com.app.fitness.presenter.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.app.fitness.common.base.BaseFragment
import com.app.fitness.common.extention.*
import com.app.fitness.databinding.FragmentHomeBinding
import com.app.fitness.domain.model.Session
import com.app.fitness.domain.model.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), View.OnClickListener {
    private val viewModel by viewModels<HomeViewModel>()
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!viewModel.currentSessionLiveData.hasActiveObservers())
            observe(viewModel.currentSessionLiveData, ::sessionObserver)
        viewModel.getCurrentSession()

    }

    private fun sessionObserver(session: Session?) {
        when (session?.status) {
            Status.START -> {
                binding.btnStart.toGone()
                binding.btnResume.toGone()
                binding.btnPause.toVisible()
                binding.btnEnd.toVisible()
                activity?.startLocationTracker()
            }
            Status.PAUSE -> {
                binding.btnPause.toGone()
                binding.btnResume.toVisible()
                binding.btnEnd.toVisible()
                activity?.stopLocationTracker()
            }

            Status.FINISHED -> {
                binding.btnStart.toVisible()
                binding.btnPause.toGone()
                binding.btnResume.toGone()
                binding.btnEnd.toGone()
            }

        }

        binding.tvSteps.text=(session?.steps?:0).toString()

    }

    override fun setListener(){
        binding.btnStart.setOnClickListener(this)
        binding.btnPause.setOnClickListener(this)
        binding.btnResume.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.btnStart.id -> startTracking()
            binding.btnPause.id -> viewModel.pauseSession()
            binding.btnResume.id -> viewModel.resumeSession()


        }
    }


    private fun startTracking() {
        activity?.checkPermissions(
            arrayListOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            granted = {
                viewModel.startSession()
            },
            denied = {}
        )


    }

}