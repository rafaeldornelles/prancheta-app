package com.rafael.featurebriefing.domain.repository

import com.rafael.core.model.BriefingForm
import com.rafael.core.model.BriefingQuestion

interface BriefingRepository {
    suspend fun getDefaultQuestions(): List<BriefingQuestion>
    suspend fun sendBriefing(clientName: String, clientEmail: String, architectId: String, questions: List<BriefingQuestion>)
    suspend fun getForm(formId: String) : BriefingForm?
    suspend fun updateBriefing(formId: String, form: BriefingForm)
    suspend fun getBriefings(userId: String): List<BriefingForm>
}