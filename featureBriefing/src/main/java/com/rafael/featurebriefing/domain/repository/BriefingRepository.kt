package com.rafael.featurebriefing.domain.repository

import com.rafael.featurebriefing.domain.entity.BriefingForm
import com.rafael.featurebriefing.domain.entity.BriefingQuestion

interface BriefingRepository {
    suspend fun getDefaultQuestions(): List<BriefingQuestion>
    suspend fun sendBriefing(clientName: String, clientEmail: String, questions: List<BriefingQuestion>)
    suspend fun getForm(formId: String) : BriefingForm?
}