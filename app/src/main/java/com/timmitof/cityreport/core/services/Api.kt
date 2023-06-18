package com.timmitof.cityreport.core.services

import com.timmitof.cityreport.models.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("complaint/get-complaints")
    suspend fun getAllComplaints(@Query("userId")userId: Int?): Response<List<Complaint>>

    @POST("complaint/add-complaint")
    suspend fun sendComplaint(@Body complaintRequest: ComplaintRequest): Response<Unit>

    @PUT("complaint/put-complaint-importance")
    suspend fun sendImportance(
        @Query("userId") userId: Int?,
        @Query("complaintId") complaintId: Int?,
        @Query("importance") importance: Int?
    ): Response<Unit>
}