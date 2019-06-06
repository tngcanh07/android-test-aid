package com.github.tngcanh07.aid.layout.analysis

import android.view.InflateException
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime

/**
 * Created by toannguyen
 * Jun 06, 2019 at 14:14
 */
fun analysisLayouts(
  container: ViewGroup,
  resPackageName: String,
  ignores: List<String> = emptyList()
): List<LayoutAnalysis> {
  val packageName = container.context.packageName
  val resources = container.resources
  val layoutInflater = LayoutInflater.from(container.context)

  val ignorePatterns = ignores.map(String::toRegex)

  val results = ArrayList<LayoutAnalysis>()
  val fields = Class.forName("$resPackageName.R\$layout")
      .fields
  for (field in fields) {
    if (ignorePatterns.firstOrNull { it.matches(field.name) } == null) {
      container.removeAllViews()
      val layoutId = resources.getIdentifier(field.name, "layout", packageName)
      results.add(analysisLayout(layoutInflater, container, field.name, layoutId))
    }
  }
  container.removeAllViews()
  return results
}

fun analysisLayout(
  layoutInflater: LayoutInflater,
  container: ViewGroup,
  layoutName: String,
  layoutId: Int
): LayoutAnalysis = try {
  val renderTime = measureNanoTime { layoutInflater.inflate(layoutId, container, true) }
  LayoutAnalysis.Success(layoutId, layoutName, renderTime)
} catch (e: InflateException) {
  LayoutAnalysis.Error(layoutId, layoutName, e.localizedMessage)
}

fun LayoutAnalysis.Success.isWarned(): Boolean = this.renderTime > TimeUnit.MILLISECONDS.toNanos(16)