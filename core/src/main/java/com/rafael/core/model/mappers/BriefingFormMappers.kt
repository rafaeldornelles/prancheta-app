package com.rafael.core.model.mappers

import com.rafael.core.model.BriefingForm
import com.rafael.core.model.BriefingFormQuestion
import com.rafael.core.model.mappers.BriefingFormKeys.ANSWER_KEY
import com.rafael.core.model.mappers.BriefingFormKeys.ANSWER_TIME
import com.rafael.core.model.mappers.BriefingFormKeys.ARCHITECT_ID
import com.rafael.core.model.mappers.BriefingFormKeys.CLIENT_EMAIL_KEY
import com.rafael.core.model.mappers.BriefingFormKeys.CLIENT_NAME_KEY
import com.rafael.core.model.mappers.BriefingFormKeys.ID_KEY
import com.rafael.core.model.mappers.BriefingFormKeys.PROJECT_ID_KEY
import com.rafael.core.model.mappers.BriefingFormKeys.QUESTIONS_KEY
import com.rafael.core.model.mappers.BriefingFormKeys.QUESTION_KEY

fun BriefingForm(
    id: String,
    map: Map<String, Any>
) = BriefingForm(
    id = id,
    clientName = map[CLIENT_NAME_KEY] as String,
    clientEmail = map[CLIENT_EMAIL_KEY] as String,
    architectId = map[ARCHITECT_ID] as String,
    questions = (map[QUESTIONS_KEY] as List<Map<String, Any>>).map {
        BriefingFormQuestion(it)
    }.sortedBy { it.question.order },
    answerTime = map[ANSWER_TIME] as? Long,
    projectId = map[PROJECT_ID_KEY] as? String
)

private fun BriefingFormQuestion(map: Map<String, Any>) = BriefingFormQuestion(
    question = (map[QUESTION_KEY] as Map<String, Any>).let {
        BriefingQuestion(it)
    },
    answer = map[ANSWER_KEY] as? String
)

private fun BriefingQuestion(map: Map<String, Any>) = BriefingQuestion(
    id = map[ID_KEY] as String,
    map = map
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