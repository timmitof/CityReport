package com.timmitof.cityreport.models

import com.timmitof.cityreport.core.utils.Importance

data class Complaint(
    val author: Author,
    val coordinate: Coordinate,
    val countDislike: Int,
    val countLike: Int,
    val date: String,
    val description: String,
    val fullAddress: String,
    val id: Int,
    val imageUrl: String?,
    val importance: Int?,
    val name: String,
    val status: Int
)