package com.timmitof.cityreport.models

data class Complaint(
    val author: Author,
    val countDislike: Int,
    val countLike: Int,
    val date: String,
    val description: String,
    val id: Int,
    val imageUrl: String,
    val importance: Int,
    val name: String,
    val status: Int
)