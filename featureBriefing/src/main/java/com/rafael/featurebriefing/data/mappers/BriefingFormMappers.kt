package com.rafael.featurebriefing.data.mappers

import com.google.firebase.firestore.DocumentSnapshot
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.ANSWER_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.ANSWER_TIME
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.ARCHITECT_ID
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.CLIENT_EMAIL_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.CLIENT_NAME_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.ID_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.LABEL_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.OPTIONS_FIELD
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.OPTIONS_URL_FIELD
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.ORDER_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.PLACEHOLDER_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.PROJECT_ID_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.QUESTIONS_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.QUESTION_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.QUESTION_TYPE_KEY
import com.rafael.featurebriefing.data.mappers.BriefingFormKeys.TRAILING_TEXT_FIELD
import com.rafael.featurebriefing.domain.entity.BriefingForm
import com.rafael.featurebriefing.domain.entity.BriefingFormQuestion
import com.rafael.featurebriefing.domain.entity.BriefingQuestion
import com.rafael.featurebriefing.domain.entity.QuestionType

fun BriefingForm(doc: DocumentSnapshot) = BriefingForm(
    id = doc.id,
    clientName = doc[CLIENT_NAME_KEY] as String,
    clientEmail = doc[CLIENT_EMAIL_KEY] as String,
    architectId = doc[ARCHITECT_ID] as String,
    questions = (doc[QUESTIONS_KEY] as List<Map<String, Any>>).map {
        BriefingFormQuestion(it)
    }.sortedBy { it.question.order },
    answerTime = doc[ANSWER_TIME] as? Long,
    projectId = doc[PROJECT_ID_KEY] as? String
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
    order = (map[ORDER_KEY] as Long).toInt(),
    placeholder = map[PLACEHOLDER_KEY] as? String,
    trailingText = map[TRAILING_TEXT_FIELD] as? String,
    options = map[OPTIONS_FIELD] as? List<String>,
    optionsUrl = map[OPTIONS_URL_FIELD] as? List<String>
)

private object BriefingFormKeys {
    const val CLIENT_NAME_KEY = "clientName"
    const val CLIENT_EMAIL_KEY = "clientEmail"
    const val QUESTIONS_KEY = "questions"
    const val QUESTION_KEY = "question"
    const val ARCHITECT_ID = "architectId"
    const val ID_KEY = "id"
    const val QUESTION_TYPE_KEY = "questionType"
    const val LABEL_KEY = "label"
    const val ANSWER_KEY = "answer"
    const val ORDER_KEY = "order"
    const val ANSWER_TIME = "answerTime"
    const val PLACEHOLDER_KEY = "placeholder"
    const val TRAILING_TEXT_FIELD = "trailingText"
    const val OPTIONS_FIELD = "options"
    const val OPTIONS_URL_FIELD = "optionsUrl"
    const val PROJECT_ID_KEY = "projectId"
}