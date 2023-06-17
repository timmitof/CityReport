package com.timmitof.cityreport.models

data class ComplaintRequest(
    val coordinate: Coordinate,
    val date: String,
    val description: String?,
    val name: String?,
    val userId: Int,
    val image: String
)