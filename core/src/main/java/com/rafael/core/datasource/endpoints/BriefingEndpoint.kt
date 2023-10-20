package com.rafael.core.datasource.endpoints

import com.rafael.core.datasource.model.AnswerBriefingRequest
import com.rafael.core.datasource.model.BriefingRequest
import com.rafael.core.datasource.model.BriefingResponse
import com.rafael.core.datasource.model.DefaultBriefingRequest
import com.rafael.core.datasource.model.DefaultBriefingResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BriefingEndpoint {
    @GET("/briefing")
    suspend fun getBriefings(): Result<List<BriefingResponse>>

    @POST("/briefing")
    suspend fun addBriefing(@Body briefing: BriefingRequest): Result<BriefingResponse>

    @GET("/briefing/{id}")
    suspend fun findById(@Path("id") id: String): Result<BriefingResponse>

    @PUT("/briefing/answer")
    suspend fun answerBriefing(@Body answerBriefingRequest: AnswerBriefingRequest): Result<BriefingResponse>

    @GET("/briefing/defaults")
    suspend fun getDefaults(): Result<List<DefaultBriefingResponse>>

    @POST("/briefing/defaults")
    suspend fun setDefaults(@Body briefings: List<DefaultBriefingRequest>): Result<List<DefaultBriefingResponse>>
}