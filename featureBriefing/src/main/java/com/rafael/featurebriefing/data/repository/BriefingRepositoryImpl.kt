package com.rafael.featurebriefing.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rafael.featurebriefing.data.mappers.BriefingForm
import com.rafael.featurebriefing.data.mappers.BriefingQuestion
import com.rafael.core.model.BriefingForm
import com.rafael.core.model.BriefingFormQuestion
import com.rafael.core.model.BriefingQuestion
import com.rafael.featurebriefing.domain.repository.BriefingRepository
import kotlinx.coroutines.tasks.await

class BriefingRepositoryImpl : BriefingRepository {
    private val db = Firebase.firestore

    override suspend fun getDefaultQuestions(): List<BriefingQuestion> {
        return db.collection(DEFAULT_BRIEFING_QUESTIONS).get().await().map {
            try {
                BriefingQuestion(it)
            } catch (e: Exception) {
                null
            }
        }.filterNotNull()
    }

    override suspend fun sendBriefing(
        clientName: String,
        clientEmail: String,
        architectId: String,
        questions: List<BriefingQuestion>
    ) {
        val briefingForm = BriefingForm(
            null, clientName, clientEmail, architectId, questions.map { BriefingFormQuestion(it) }
        )
        db.collection(BRIEFING_FORMS).add(briefingForm).await()
    }

    override suspend fun getForm(formId: String): BriefingForm? {
        val doc = db.collection(BRIEFING_FORMS).document(formId).get().await()
        return  BriefingForm(doc)
    }

    override suspend fun updateBriefing(formId: String, form: BriefingForm) {
        db.collection(BRIEFING_FORMS).document(formId).set(form)
    }

    override suspend fun getBriefings(userId: String): List<BriefingForm> {
        val doc = db.collection(BRIEFING_FORMS).whereEqualTo(ARCHITECT_ID_KEY, userId).get().await()
        return doc.map { BriefingForm(it) }
    }

    companion object {
        private const val DEFAULT_BRIEFING_QUESTIONS = "briefing_default_questions"
        private const val BRIEFING_FORMS = "briefing_forms"
        private const val ARCHITECT_ID_KEY = "architectId"
    }
}