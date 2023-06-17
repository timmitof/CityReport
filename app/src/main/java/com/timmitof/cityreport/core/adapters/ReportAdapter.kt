package com.timmitof.cityreport.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timmitof.cityreport.R
import com.timmitof.cityreport.models.ReportModel
import com.timmitof.cityreport.databinding.ItemReportsBinding

class ReportAdapter: RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {
    private var data = listOf<ReportModel>()

    inner class ReportViewHolder(val binding: ItemReportsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReportsBinding.inflate(inflater, parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val item = data[position]
        holder.binding.apply {
            username.text = item.username
            imageReport.setColorFilter(R.color.primaryColor)
            likes.text = item.likes.toString()
            descriptionReport.text = item.description
        }
    }

    override fun getItemCount() = data.size

    fun setData(list: List<ReportModel>) {
        data = list
    }
}