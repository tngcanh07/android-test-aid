package com.github.tngcanh07.aid

import android.view.View

/**
 * Created by toannguyen
 * Jun 06, 2019 at 15:17
 */
fun View.show() {
  this.visibility = View.VISIBLE
}

fun View.hide() {
  this.visibility = View.INVISIBLE
}