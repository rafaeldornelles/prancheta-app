package com.rafael.featurebriefing.data.repository

import com.rafael.core.datasource.endpoints.BriefingEndpoint
import com.rafael.core.datasource.model.AnswerBriefingRequest
import com.rafael.core.datasource.model.BriefingRequest
import com.rafael.core.datasource.model.BriefingResponse
import com.rafael.core.datasource.model.DefaultBriefingResponse
import com.rafael.featurebriefing.domain.repository.BriefingRepository

class BriefingRepositoryImpl(
    val endpoint: BriefingEndpoint
) : BriefingRepository {

    override suspend fun getDefaultQuestions(): Result<List<DefaultBriefingResponse>> {
        return endpoint.getDefaults()
    }

    override suspend fun sendBriefing(
        briefing: BriefingRequest
    ): Result<BriefingResponse> {
        return endpoint.addBriefing(briefing)
    }

    override suspend fun findById(briefingId: String): Result<BriefingResponse> {
        return endpoint.findById(briefingId)
    }

    override suspend fun answerBriefing(request: AnswerBriefingRequest): Result<BriefingResponse> {
        return endpoint.answerBriefing(request)
    }

    override suspend fun getBriefings(): Result<List<BriefingResponse>> {
        return endpoint.getBriefings()
    }
}