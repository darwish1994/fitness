package com.app.fitness.presenter.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.fitness.R
import com.app.fitness.common.extention.observe
import com.app.fitness.common.extention.viewBinding
import com.app.fitness.databinding.FragmentHistoryBinding
import com.app.fitness.domain.model.Session
import com.app.fitness.presenter.history.list.SessionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {
    private val binding by viewBinding(FragmentHistoryBinding::bind)

    private val viewModel by viewModels<HistoryViewModel>()

    private val adapter by lazy {
        SessionAdapter {
            findNavController().navigate(HistoryFragmentDirections.actionViewHistoryToDetailsFragment(it.id!!))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recSession.adapter = adapter
        if (!viewModel.sessionsLiveData.hasActiveObservers())
            observe(viewModel.sessionsLiveData, ::sessionObserver)
        viewModel.getSessions()


    }


    private fun sessionObserver(it: List<Session>?) {
        adapter.updateList(it ?: arrayListOf())
    }

}
