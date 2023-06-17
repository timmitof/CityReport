package com.timmitof.cityreport.models

data class LoginResponse(
    val isSuccess: Boolean,
    val message: String,
    val statusCode: Int,
    val userId: Int
)