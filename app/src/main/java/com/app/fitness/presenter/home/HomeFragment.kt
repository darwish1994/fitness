package com.app.fitness.presenter.home

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.app.fitness.R
import com.app.fitness.common.extention.*
import com.app.fitness.databinding.FragmentHomeBinding
import com.app.fitness.domain.model.Session
import com.app.fitness.domain.model.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // check if it has register observer before
        if (!viewModel.currentSessionLiveData.hasActiveObservers())
            observe(viewModel.currentSessionLiveData, ::sessionObserver)

        // check if it has register observer before
        if (!viewModel.timerLiveData.hasActiveObservers())
            observe(viewModel.timerLiveData,::timerObserver)

        // get current session updates
        viewModel.getCurrentSession()

        setListener()

    }


    /***
     * observer for current session
     * observe changes happen for it
     * */
    private fun sessionObserver(session: Session?) {
        binding.tvSteps.text = (session?.steps ?: 0).toString()
        binding.tvDistance.text = viewModel.getDistanceCovered(session?.steps ?: 0)

        when (session?.status) {
            Status.START -> sessionStarted()
            Status.PAUSE -> sessionPaused()
            Status.FINISHED -> sessionFinished()
        }



    }

    private fun setListener() {
        binding.btnStart.setOnClickListener(this)
        binding.btnPause.setOnClickListener(this)
        binding.btnResume.setOnClickListener(this)
        binding.btnEnd.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.btnStart.id -> startTracking()
            binding.btnPause.id -> viewModel.pauseSession()
            binding.btnResume.id -> viewModel.resumeSession()
            binding.btnEnd.id -> viewModel.endSession()
        }
    }

    /**
     * check for location permission
     * then create a session
     * to start location trackting
     * */

    private fun startTracking() {
        activity?.checkPermissions(
            arrayListOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION

            ).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    add(android.Manifest.permission.ACTIVITY_RECOGNITION)
                }
            },
            granted = {
                viewModel.startSession()
            },
            denied = {}
        )

    }


    /**
     * observer fun for timer live data
     * **/
    private fun timerObserver(time:String?){
        binding.tvTimer.text=time
    }


    /**
     * fun responsible for action happen when session started
     * */
    private fun sessionStarted(){
        viewModel.startTimer()
        binding.btnStart.toGone()
        binding.btnResume.toGone()
        binding.btnPause.toVisible()
        binding.btnEnd.toVisible()
        activity?.startLocationTracker()
    }
    // action happen when user pause session
    private fun sessionPaused(){
        viewModel.pauseTimer()
        binding.btnStart.toGone()
        binding.btnPause.toGone()
        binding.btnResume.toVisible()
        binding.btnEnd.toVisible()
        activity?.stopLocationTracker()
    }

    private fun sessionFinished(){
        viewModel.resetTimer()
        binding.tvSteps.text="0"
        binding.tvDistance.text="0"
        binding.btnStart.toVisible()
        binding.btnPause.toGone()
        binding.btnResume.toGone()
        binding.btnEnd.toGone()
        activity?.stopLocationTracker()
    }
}