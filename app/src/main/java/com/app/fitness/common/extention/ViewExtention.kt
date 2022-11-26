package com.app.fitness.common.extention

import android.view.View

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toInVisible() {
    visibility = View.INVISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}