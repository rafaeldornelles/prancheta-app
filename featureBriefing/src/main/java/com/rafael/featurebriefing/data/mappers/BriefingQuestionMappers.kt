package com.rafael.featurebriefing.data.mappers

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.rafael.featurebriefing.domain.entity.BriefingQuestion
import com.rafael.featurebriefing.domain.entity.QuestionType

fun BriefingQuestion(doc: QueryDocumentSnapshot) = BriefingQuestion(
    id = doc.id,
    questionType = (doc[BriefingQuestionKeys.QUESTION_TYPE_FIELD] as String).let { QuestionType.valueOf(it) },
    label = doc[BriefingQuestionKeys.LABEL_FIELD] as String,
    order = (doc[BriefingQuestionKeys.ORDER_FIELD] as Long).toInt(),
    placeholder = doc[BriefingQuestionKeys.PLACEHOLDER_FIELD] as? String,
    trailingText = doc[BriefingQuestionKeys.TRAILING_TEXT_FIELD] as? String,
    options = doc[BriefingQuestionKeys.OPTIONS_FIELD] as? List<String>,
    optionsUrl = doc[BriefingQuestionKeys.OPTIONS_URL_FIELD] as? List<String>
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