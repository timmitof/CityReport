package com.timmitof.cityreport.core.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timmitof.cityreport.R
import com.timmitof.cityreport.core.utils.Constants
import com.timmitof.cityreport.core.utils.Importance
import com.timmitof.cityreport.core.utils.StatusComplaince
import com.timmitof.cityreport.databinding.ItemReportsBinding
import com.timmitof.cityreport.models.Complaint
import com.timmitof.cityreport.models.ImportanceRequest

class ReportAdapter(private val importance: (ImportanceRequest) -> Unit): RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {
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
            Glide.with(holder.itemView).load("${Constants.BASE_URL}Photos/${item.imageUrl}").centerCrop().into(imageReport)
            username.text = item.name
            likes.text = item.countLike.toString()
            descriptionReport.text = item.description
            status.text = when(item.status) {
                StatusComplaince.Reported.value -> { "Отправлено" }
                StatusComplaince.Reviewed.value -> { "На рассмотрении" }
                StatusComplaince.Performed.value -> { "В работе" }
                StatusComplaince.Finished.value -> { "Выполнено" }
                else -> {""}
            }
            when(item.importance) {
                Importance.Like.value -> { likeButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.red)) }
                Importance.Dislike.value -> { likeButton.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.gray)) }
            }
            likeButton.setOnClickListener {
                when(item.importance) {
                    Importance.Like.value -> { importance.invoke(ImportanceRequest(userId = null, complaintId = item.id, importance = Importance.Dislike)) }
                    Importance.Dislike.value -> { importance.invoke(ImportanceRequest(userId = null, complaintId = item.id, importance = Importance.Like)) }
                    null -> { importance.invoke(ImportanceRequest(userId = null, complaintId = item.id, importance = Importance.Dislike)) }
                }
            }
        }
    }

    override fun getItemCount() = data.size

    fun setData(list: List<Complaint>) {
        data = list
        notifyDataSetChanged()
    }
}