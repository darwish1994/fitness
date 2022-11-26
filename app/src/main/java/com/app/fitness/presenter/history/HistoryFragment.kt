package com.app.fitness.presenter.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.fitness.R
import com.app.fitness.common.extention.navTo
import com.app.fitness.common.extention.observe
import com.app.fitness.common.extention.viewBinding
import com.app.fitness.databinding.FragmentHistoryBinding
import com.app.fitness.domain.model.Session
import com.app.fitness.presenter.history.list.SessionAdapter
import com.app.fitness.presenter.history.list.SessionOnClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history), SessionOnClick {
    private val binding by viewBinding(FragmentHistoryBinding::bind)

    private val viewModel by viewModels<HistoryViewModel>()

    private val adapter by lazy {
        SessionAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recSession.adapter = adapter
        if (!viewModel.sessionsLiveData.hasActiveObservers())
            observe(viewModel.sessionsLiveData, ::sessionObserver)
        viewModel.getSessions()

        adapter.setListener(this)

    }

    override fun onDestroyView() {
        adapter.setListener(null)
        super.onDestroyView()
    }


    private fun sessionObserver(it: List<Session>?) {
        adapter.updateList(it ?: arrayListOf())
    }

    override fun onSessionClicked(session: Session) {
        session.id?.let { sessionId ->
            navTo(HistoryFragmentDirections.actionViewHistoryToDetailsFragment(sessionId))
        }

    }

}
