package com.rafael.core.model.mappers

import com.rafael.core.model.BriefingQuestion
import com.rafael.core.model.QuestionType
import com.rafael.core.model.mappers.BriefingQuestionKeys.LABEL_FIELD
import com.rafael.core.model.mappers.BriefingQuestionKeys.OPTIONS_FIELD
import com.rafael.core.model.mappers.BriefingQuestionKeys.OPTIONS_URL_FIELD
import com.rafael.core.model.mappers.BriefingQuestionKeys.ORDER_FIELD
import com.rafael.core.model.mappers.BriefingQuestionKeys.PLACEHOLDER_FIELD
import com.rafael.core.model.mappers.BriefingQuestionKeys.QUESTION_TYPE_FIELD
import com.rafael.core.model.mappers.BriefingQuestionKeys.TRAILING_TEXT_FIELD

fun BriefingQuestion(
    id: String,
    map: Map<String, Any>
) = BriefingQuestion(
    id = id,
    questionType = (map[QUESTION_TYPE_FIELD] as String).let { QuestionType.valueOf(it) },
    label = map[LABEL_FIELD] as String,
    order = (map[ORDER_FIELD] as Long).toInt(),
    placeholder = map[PLACEHOLDER_FIELD] as? String,
    trailingText = map[TRAILING_TEXT_FIELD] as? String,
    options = map[OPTIONS_FIELD] as? List<String>,
    optionsUrl = map[OPTIONS_URL_FIELD] as? List<String>
)

private object BriefingQuestionKeys {
    const val QUESTION_TYPE_FIELD = "questionType"
    const val LABEL_FIELD = "label"
    const val ORDER_FIELD = "order"
    const val PLACEHOLDER_FIELD = "placeholder"
    const val TRAILING_TEXT_FIELD = "trailingText"
    const val OPTIONS_FIELD = "options"
    const val OPTIONS_URL_FIELD = "optionsUrl"
}