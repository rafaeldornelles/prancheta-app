package com.rafael.featurebriefing.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.rafael.featurebriefing.data.mappers.BriefingForm
import com.rafael.featurebriefing.data.mappers.BriefingQuestion
import com.rafael.featurebriefing.domain.entity.BriefingForm
import com.rafael.featurebriefing.domain.entity.BriefingFormQuestion
import com.rafael.featurebriefing.domain.entity.BriefingQuestion
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
            clientName, clientEmail, architectId, questions.map { BriefingFormQuestion(it) }
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

    companion object {
        private const val DEFAULT_BRIEFING_QUESTIONS = "briefing_default_questions"
        private const val BRIEFING_FORMS = "briefing_forms"
    }
}