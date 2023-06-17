package com.timmitof.cityreport.core.services

import com.timmitof.cityreport.models.Complaint
import com.timmitof.cityreport.models.ComplaintRequest
import com.timmitof.cityreport.models.LoginRequest
import com.timmitof.cityreport.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("complaint/get-complaints")
    suspend fun getAllComplaints(): Response<List<Complaint>>

    @POST("complaint/add-complaint")
    suspend fun sendComplaint(@Body complaintRequest: ComplaintRequest): Response<Unit>
}