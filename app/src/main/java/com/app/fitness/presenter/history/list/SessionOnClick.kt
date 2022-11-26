package com.app.fitness.presenter.history.list

import com.app.fitness.data.model.Session

interface SessionOnClick {
    fun onSessionClicked(session: Session)
}