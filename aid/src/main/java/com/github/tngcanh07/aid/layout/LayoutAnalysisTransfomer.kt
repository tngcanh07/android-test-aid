package com.github.tngcanh07.aid.layout

import com.github.tngcanh07.aid.layout.analysis.LayoutAnalysis
import com.github.tngcanh07.aid.layout.analysis.LayoutAnalysis.Success
import com.github.tngcanh07.aid.layout.analysis.isWarned
import java.text.DecimalFormat

/**
 * Created by toannguyen
 * Jun 06, 2019 at 14:49
 */
private val formatter = DecimalFormat()

fun LayoutAnalysis.getLayoutInfo(): String = "$layoutName - 0x${layoutId.toString(16)}"
fun Success.getFormattedRenderTime(): String = formatter.format(this.renderTime)

fun sort(items: List<LayoutAnalysis>): List<LayoutAnalysis> =
  items.sortedWith(Comparator { o1, o2 ->
    return@Comparator when {
      o1 is Success && o2 is Success -> {
        when {
          o1.isWarned() == o2.isWarned() -> (o2.renderTime - o1.renderTime).toInt()
          o1.isWarned() -> -1
          else -> 1
        }
      }
      o1 is Success -> 1
      o2 is Success -> -1
      else -> o1.layoutName.compareTo(o2.layoutName)
    }
  })