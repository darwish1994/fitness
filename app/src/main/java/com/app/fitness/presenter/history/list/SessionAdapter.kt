package com.app.fitness.presenter.history.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.fitness.presenter.history.diff.SessionDiffUtil
import com.app.fitness.common.extention.getDistanceCovered
import com.app.fitness.common.extention.timerFormat
import com.app.fitness.databinding.ItemLayoutSessionBinding
import com.app.fitness.domain.model.Session

class SessionAdapter : RecyclerView.Adapter<SessionAdapter.SessionViewHolder>() {

    private val dataList = arrayListOf<Session>()

    private var sessionOnClick:SessionOnClick?=null

    fun setListener(listener: SessionOnClick?){
        sessionOnClick=listener
    }

    fun updateList(data: List<Session>) {
        val diffUtil = SessionDiffUtil(dataList, data)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        diffResults.dispatchUpdatesTo(this)
        dataList.apply {
            clear()
            addAll(data)
        }
    }


    inner class SessionViewHolder(private val layout: ItemLayoutSessionBinding) :
        RecyclerView.ViewHolder(layout.root) {

        init {
            layout.root.setOnClickListener {
                sessionOnClick?.onSessionClicked(dataList[adapterPosition])
            }
        }

        fun binding(item: Session) {
            layout.tvDistance.text = item.steps?.getDistanceCovered()
            layout.tvSteps.text = item.steps.toString()
            layout.tvDuration.text = item.duration?.timerFormat()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder =
        SessionViewHolder(
            ItemLayoutSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.binding(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}