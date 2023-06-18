package com.timmitof.cityreport.models

import com.timmitof.cityreport.core.utils.Importance

data class ImportanceRequest(
    val userId: Int?,
    val importance: Importance,
    val complaintId: Int
)
