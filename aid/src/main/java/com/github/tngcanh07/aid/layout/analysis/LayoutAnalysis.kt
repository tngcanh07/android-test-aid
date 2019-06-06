package com.github.tngcanh07.aid.layout.analysis

/**
 * Created by toannguyen
 * Jun 06, 2019 at 14:08
 */
sealed class LayoutAnalysis {
  abstract val layoutId: Int
  abstract val layoutName: String

  data class Success(
    override val layoutId: Int,
    override val layoutName: String,
    val renderTime: Long
  ) : LayoutAnalysis()

  data class Error(
    override val layoutId: Int,
    override val layoutName: String,
    val errorMessage: String
  ) : LayoutAnalysis()
}

