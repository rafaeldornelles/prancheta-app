package com.rafael.featurebriefing.domain.repository

import com.rafael.featurebriefing.domain.entity.BriefingQuestion

interface BriefingRepository {
    suspend fun getDefaultQuestions(): List<BriefingQuestion>
}