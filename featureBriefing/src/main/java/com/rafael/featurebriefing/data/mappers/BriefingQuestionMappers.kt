package com.rafael.featurebriefing.data.mappers

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.rafael.featurebriefing.domain.entity.BriefingQuestion
import com.rafael.featurebriefing.domain.entity.QuestionType

fun BriefingQuestion(doc: QueryDocumentSnapshot) = BriefingQuestion(
    id = doc.id,
    questionType = (doc[BriefingQuestionKeys.QUESTION_TYPE_FIELD] as String).let { QuestionType.valueOf(it) },
    label = doc[BriefingQuestionKeys.LABEL_FIELD] as String,
    order = (doc[BriefingQuestionKeys.ORDER_FIELD] as Long).toInt()
)

private object BriefingQuestionKeys {
    const val QUESTION_TYPE_FIELD = "questionType"
    const val LABEL_FIELD = "label"
    const val ORDER_FIELD = "order"

}