package com.timmitof.cityreport.core.services

import com.timmitof.cityreport.models.*
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Api
) {

    suspend fun login(phoneNumber: String, password: String): Response<LoginResponse> {
        val loginRequest = LoginRequest(phoneNumber, password)
        return api.login(loginRequest)
    }

    suspend fun register(phoneNumber: String, password: String): Response<LoginResponse> {
        val loginRequest = LoginRequest(phoneNumber, password)
        return api.register(loginRequest)
    }

    suspend fun getAllComplaints(userId: Int?): Response<List<Complaint>> {
        return api.getAllComplaints(userId)
    }

    suspend fun sendComplaint(complaintRequest: ComplaintRequest): Response<Unit> {
        return api.sendComplaint(complaintRequest)
    }

    suspend fun sendImportance(importanceRequest: ImportanceRequest): Response<Unit> {
        return api.sendImportance(userId = importanceRequest.userId, complaintId = importanceRequest.complaintId, importance = importanceRequest.importance.value)
    }
}