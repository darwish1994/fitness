package com.app.fitness.common.extention

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController


fun Fragment.navTo(directions: NavDirections){
    findNavController().navigate(directions)
}