package com.rafael.featurebriefing.data.repository

import android.util.Log
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.rafael.featurebriefing.data.repository.BriefingQuestionKeys.LABEL_FIELD
import com.rafael.featurebriefing.data.repository.BriefingQuestionKeys.ORDER_FIELD
import com.rafael.featurebriefing.data.repository.BriefingQuestionKeys.QUESTION_TYPE_FIELD
import com.rafael.featurebriefing.domain.entity.BriefingQuestion
import com.rafael.featurebriefing.domain.entity.QuestionType
import com.rafael.featurebriefing.domain.repository.BriefingRepository
import kotlinx.coroutines.tasks.await

class BriefingRepositoryImpl : BriefingRepository {
    private val db = Firebase.firestore

    override suspend fun getDefaultQuestions(): List<BriefingQuestion> {
        return db.collection(DEFAULT_BRIEFING_QUESTIONS).get().await().map {
            try {
                it.toBriefingQuestion()
            } catch (e: Exception) {
                Log.d("AAA", e.toString())
                null
            }
        }.filterNotNull()
    }

    companion object {
        private const val DEFAULT_BRIEFING_QUESTIONS = "briefing_default_questions"
    }
}

private object BriefingQuestionKeys {
    const val QUESTION_TYPE_FIELD = "questionType"
    const val LABEL_FIELD = "label"
    const val ORDER_FIELD = "order"

}

private fun QueryDocumentSnapshot.toBriefingQuestion() = BriefingQuestion(
    id = id,
    questionType = (get(QUESTION_TYPE_FIELD) as String).let { QuestionType.valueOf(it) },
    label = get(LABEL_FIELD) as String,
    order = (get(ORDER_FIELD) as Long).toInt()
)