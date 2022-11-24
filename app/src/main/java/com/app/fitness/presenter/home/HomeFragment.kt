package com.app.fitness.presenter.home

import com.app.fitness.common.base.BaseFragment
import com.app.fitness.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

}