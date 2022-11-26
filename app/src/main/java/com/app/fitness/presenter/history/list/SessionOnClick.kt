package com.app.fitness.presenter.history.list

import com.app.fitness.domain.model.Session

interface SessionOnClick {
    fun onSessionClicked(session: Session)
}