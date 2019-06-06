package com.github.tngcanh07.aid.layout

import android.app.Activity
import android.os.Bundle
import com.github.tngcanh07.aid.R
import kotlinx.android.synthetic.main.analysis_layout_activity.containerView
import kotlinx.android.synthetic.main.analysis_layout_activity.statusText
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime

/**
 * Created by toannguyen
 * Jun 05, 2019 at 17:13
 */
class LayoutAnalysisActivity : Activity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.analysis_layout_activity)

    val resPackageName = intent?.extras?.getString(EXTRA_RES_PACKAGE_NAME)
    if (resPackageName.isNullOrBlank()) {
      throw IllegalArgumentException("Resource's package is required")
    }

    analysis(resPackageName)
  }

  private fun analysis(resPackageName: String) {
    val fields = Class.forName("$resPackageName.R\$layout")
        .fields
    val formatter = DecimalFormat()
    val resultBuilder = StringBuilder("$resPackageName\n")
    for (field in fields) {
      try {
        val resId = resources.getIdentifier(field.name, "layout", packageName)
        val time = measureNanoTime {
          containerView.removeAllViews()
          layoutInflater.inflate(resId, containerView, true)
        }
        if (time > TimeUnit.MILLISECONDS.toNanos(16)) {
          resultBuilder.append("*")
        }
        resultBuilder.append(formatter.format(time))
        resultBuilder.append("\t: ")
        resultBuilder.append(field.name)
        resultBuilder.append('\n')
      } catch (e: Exception) {
        resultBuilder.append("ERROR\t: ")
        resultBuilder.append(field.name)
        resultBuilder.append('\n')
        resultBuilder.append(e.localizedMessage)
        resultBuilder.append('\n')
      }
    }
    statusText.text = resultBuilder.toString()
  }

  companion object {
    const val EXTRA_RES_PACKAGE_NAME = "extra.packageName"
  }
}