package com.app.fitness.presenter.history.diff

import androidx.recyclerview.widget.DiffUtil
import com.app.fitness.domain.model.Session

class SessionDiffUtil (private val oldList: List<Session>, private val newList: List<Session>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].status != newList[newItemPosition].status -> false
            oldList[oldItemPosition].steps != newList[newItemPosition].steps -> false
            oldList[oldItemPosition].duration != newList[newItemPosition].duration -> false
            else -> true
        }
    }
}