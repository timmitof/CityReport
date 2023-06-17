package com.timmitof.cityreport.core.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timmitof.cityreport.core.utils.Constants
import com.timmitof.cityreport.databinding.ItemReportsBinding
import com.timmitof.cityreport.models.Complaint

class ReportAdapter: RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {
    private var data = listOf<Complaint>()

    inner class ReportViewHolder(val binding: ItemReportsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReportsBinding.inflate(inflater, parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val item = data[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load("${Constants.BASE_URL}Photos/${item.imageUrl}").into(imageReport)
            username.text = item.name
            likes.text = item.countLike.toString()
            descriptionReport.text = item.description
        }
    }

    override fun getItemCount() = data.size

    fun setData(list: List<Complaint>) {
        data = list
        notifyDataSetChanged()
    }
}