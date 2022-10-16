package com.rafael.featurebriefing.data.mappers

import com.google.firebase.firestore.DocumentSnapshot
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.ANSWER_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.CLIENT_EMAIL_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.CLIENT_NAME_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.ID_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.LABEL_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.ORDER_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.QUESTIONS_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.QUESTION_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.QUESTION_TYPE_KEY
import com.rafael.featurebriefing.domain.entity.BriefingForm
import com.rafael.featurebriefing.domain.entity.BriefingFormQuestion
import com.rafael.featurebriefing.domain.entity.BriefingQuestion
import com.rafael.featurebriefing.domain.entity.QuestionType

fun BriefingForm(doc: DocumentSnapshot) = BriefingForm(
    clientName = doc[CLIENT_NAME_KEY] as String,
    clientEmail = doc[CLIENT_EMAIL_KEY] as String,
    questions = (doc[QUESTIONS_KEY] as List<Map<String, Any>>).map {
        BriefingFormQuestion(it)
    }
)

private fun BriefingFormQuestion(map: Map<String, Any>) = BriefingFormQuestion(
    question = (map[QUESTION_KEY] as Map<String, Any>).let {
        BriefingQuestion(it)
    },
    answer = map[ANSWER_KEY] as? String
)

private fun BriefingQuestion(map: Map<String, Any>) = BriefingQuestion(
    id = map[ID_KEY] as String,
    questionType = QuestionType.valueOf(map[QUESTION_TYPE_KEY] as String),
    label = map[LABEL_KEY] as String,
    order = (map[ORDER_KEY] as Long).toInt()
)

private object BriefingFormKeys {
    const val CLIENT_NAME_KEY = "clientName"
    const val CLIENT_EMAIL_KEY = "clientEmail"
    const val QUESTIONS_KEY = "questions"
    const val QUESTION_KEY = "question"
    const val ID_KEY = "id"
    const val QUESTION_TYPE_KEY = "questionType"
    const val LABEL_KEY = "label"
    const val ANSWER_KEY = "answer"
    const val ORDER_KEY = "order"
}