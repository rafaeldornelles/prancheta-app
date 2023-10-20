package com.rafael.core.model

import com.rafael.core.datasource.model.QuestionResponse
import com.rafael.core.datasource.model.QuestionTypeResponse
import java.util.Date

data class BriefingForm(
    val id: String? = null,
    val clientName: String,
    val clientEmail: String,
    val architectId: String,
    val questions: List<BriefingFormQuestion>,
    val answerTime: Long? = null,
    val projectId: String? = null,
    val sendedAt: Date?
)

data class BriefingFormQuestion(
    val question: BriefingQuestion,
    val answer: String? = null
)

fun QuestionResponse.toBriefingFormQuestion(order: Int = 0) = BriefingFormQuestion(
    question = this.toBriefingQuestion(order),
    answer = answer
)

fun QuestionResponse.toBriefingQuestion(order: Int = 0) = BriefingQuestion(
    id = _id,
    questionType = questionType.toQuestionType(options.orEmpty().mapNotNull { it.image }
        .isNotEmpty()),
    label = caput,
    order = order,
    placeholder = placeholder,
    trailingText = trailingText,
    optionsUrl = options?.mapNotNull { it.image },
    options = options?.map { it.text },
)


fun QuestionTypeResponse.toQuestionType(hasImage: Boolean = false): QuestionType = when (this) {
    QuestionTypeResponse.TEXT -> QuestionType.TEXT
    QuestionTypeResponse.NUMBER -> QuestionType.NUMBER
    QuestionTypeResponse.CURRENCY -> QuestionType.CURRENCY
    QuestionTypeResponse.YESNO -> QuestionType.YESNO
    QuestionTypeResponse.MULTIPLE -> QuestionType.CHECKBOX
    QuestionTypeResponse.SINGLE -> if (hasImage) QuestionType.RADIOIMAGE else QuestionType.RADIO
}