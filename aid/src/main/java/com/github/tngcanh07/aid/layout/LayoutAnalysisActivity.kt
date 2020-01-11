package com.github.tngcanh07.aid.layout

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.tngcanh07.aid.R
import com.github.tngcanh07.aid.layout.analysis.analysisLayouts
import com.github.tngcanh07.aid.layout.rendering.LayoutRenderingActivity
import kotlinx.android.synthetic.main.analysis_layout_activity.containerView
import kotlinx.android.synthetic.main.analysis_layout_activity.recyclerView

/**
 * Created by toannguyen
 * Jun 05, 2019 at 17:13
 */
class LayoutAnalysisActivity : Activity() {
  private lateinit var adapter: LayoutAnalysisAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.analysis_layout_activity)

    val resPackageName = intent?.extras?.getString(EXTRA_RES_PACKAGE_NAME)
    val ignores = intent?.extras?.getStringArrayList(EXTRA_IGNORE_NAMES) ?: emptyList<String>()
    if (resPackageName.isNullOrBlank()) {
      throw IllegalArgumentException("Resource's package is required")
    }

    adapter = LayoutAnalysisAdapter {
      LayoutRenderingActivity.startActivity(this, it.layoutId)
    }
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = adapter

    val data = analysisLayouts(containerView, resPackageName, ignores)
    adapter.bind(sort(data))
  }

  companion object {
    const val EXTRA_RES_PACKAGE_NAME = "extra.packageName"
    const val EXTRA_IGNORE_NAMES = "extra.ignoreNames"
  }
}