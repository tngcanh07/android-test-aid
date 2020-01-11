package com.github.tngcanh07.aid.layout.rendering

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.github.tngcanh07.aid.R

class LayoutRenderingActivity : AppCompatActivity() {
  lateinit var rootView: FrameLayout

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    rootView = FrameLayout(this)
    setContentView(rootView)
    val layoutResId = intent.extras?.getInt(EXTRA_LAYOUT_RES_ID) ?: 0
    require(layoutResId > 0) { "layoutResId is required" }
    layoutInflater.inflate(layoutResId, rootView, true)

    setViewBackground(rootView, true)
  }

  private fun setViewBackground(itemView: View, ignoreRoot: Boolean = false) {
    if(!ignoreRoot) {
      itemView.setBackgroundResource(R.drawable.view_background)
    }
    if (itemView is ViewGroup) {
      for (i in 0 until itemView.childCount) {
        setViewBackground(itemView.getChildAt(i))
      }
    }
  }

  companion object {
    private const val EXTRA_LAYOUT_RES_ID = "extra.layoutResId"

    fun startActivity(
      context: Context,
      layoutResId: Int
    ) {
      Intent(context, LayoutRenderingActivity::class.java)
          .putExtra(EXTRA_LAYOUT_RES_ID, layoutResId)
          .let(context::startActivity)
    }
  }
}