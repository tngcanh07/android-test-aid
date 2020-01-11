package com.github.tngcanh07.aid.layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.tngcanh07.aid.R
import com.github.tngcanh07.aid.gone
import com.github.tngcanh07.aid.layout.analysis.LayoutAnalysis
import com.github.tngcanh07.aid.layout.analysis.LayoutAnalysis.Error
import com.github.tngcanh07.aid.layout.analysis.LayoutAnalysis.Success
import com.github.tngcanh07.aid.layout.analysis.isWarned
import com.github.tngcanh07.aid.show
import java.util.ArrayList

/**
 * Created by toannguyen
 * Jun 06, 2019 at 14:55
 */
class LayoutAnalysisAdapter(
  private val onClick: (LayoutAnalysis) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private val mData = ArrayList<LayoutAnalysis>()

  fun bind(items: List<LayoutAnalysis>) {
    mData.clear()
    mData.addAll(items)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): RecyclerView.ViewHolder {
    return when (viewType) {
      TYPE_SUCCESS -> SuccessViewHolder(parent, onClick)
      TYPE_ERROR -> ErrorViewHolder(parent, onClick)
      else -> throw IllegalArgumentException("illegal viewType: $viewType")
    }
  }

  override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    position: Int
  ) {
    when (holder) {
      is SuccessViewHolder -> holder.bind(mData[position] as Success)
      is ErrorViewHolder -> holder.bind(mData[position] as Error)
    }
  }

  override fun getItemCount(): Int = mData.size

  override fun getItemViewType(position: Int): Int {
    return when (mData[position]) {
      is Error -> TYPE_ERROR
      is Success -> TYPE_SUCCESS
    }
  }

  companion object {
    private const val TYPE_SUCCESS = 0
    private const val TYPE_ERROR = 1
  }
}

private class SuccessViewHolder(
  container: ViewGroup,
  onclick: (Success) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(container.context)
        .inflate(R.layout.analysis_layout_success_item, container, false)
) {
  private val warningIcon: View = itemView.findViewById(R.id.warningIcon)
  private val layoutInfo: TextView = itemView.findViewById(R.id.layoutInfo)
  private val renderTimeText: TextView = itemView.findViewById(R.id.renderTimeText)

  private var model: Success? = null

  init {
    itemView.setOnClickListener { model?.let(onclick) }
  }

  fun bind(model: Success) {
    if (model.isWarned()) {
      warningIcon.show()
      itemView.setBackgroundResource(R.color.warning_background)
    } else {
      warningIcon.gone()
      itemView.setBackgroundResource(R.color.normal_background)
    }
    renderTimeText.text = model.getFormattedRenderTime()
    layoutInfo.text = model.getLayoutInfo()
    this.model = model
  }

}

private class ErrorViewHolder(
  container: ViewGroup,
  onclick: (Error) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(container.context)
        .inflate(R.layout.analysis_layout_error_item, container, false)
) {
  private val layoutInfo: TextView = itemView.findViewById(R.id.layoutInfo)
  private val errorMessage: TextView = itemView.findViewById(R.id.errorMessage)

  private var model: Error? = null

  init {
    itemView.setOnClickListener { model?.let(onclick) }
  }

  fun bind(model: Error) {
    layoutInfo.text = model.getLayoutInfo()
    errorMessage.text = model.errorMessage
    this.model = model
  }

}