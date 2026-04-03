package com.donsgoorm.app.data.remote

import retrofit2.http.GET

interface ApiService {
    @GET("health")
    suspend fun health(): Map<String, String>
}
