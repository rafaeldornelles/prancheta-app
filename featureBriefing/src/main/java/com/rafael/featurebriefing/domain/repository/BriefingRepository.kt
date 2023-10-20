package com.rafael.featurebriefing.domain.repository

import com.rafael.core.datasource.model.AnswerBriefingRequest
import com.rafael.core.datasource.model.BriefingRequest
import com.rafael.core.datasource.model.BriefingResponse
import com.rafael.core.datasource.model.DefaultBriefingResponse

interface BriefingRepository {
    suspend fun getDefaultQuestions(): Result<List<DefaultBriefingResponse>>
    suspend fun sendBriefing(briefing: BriefingRequest): Result<BriefingResponse>
    suspend fun findById(formId: String): Result<BriefingResponse>
    suspend fun answerBriefing(request: AnswerBriefingRequest): Result<BriefingResponse>
    suspend fun getBriefings(): Result<List<BriefingResponse>>
}