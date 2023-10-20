package com.rafael.core.model

import com.rafael.core.datasource.model.QuestionOption
import com.rafael.core.datasource.model.QuestionRequest
import com.rafael.core.datasource.model.QuestionTypeResponse

data class BriefingQuestion(
    val id: String,
    val questionType: QuestionType,
    val label: String,
    val order: Int,
    val placeholder: String?,
    val trailingText: String?,
    val options: List<String>?,
    val optionsUrl: List<String>?
)

enum class QuestionType(val questionLabel: String) {
    TEXT("Texto"), NUMBER("Número"), CURRENCY("Quantidade monetária"), RADIO("Escolha Simples"), RADIOIMAGE(
        "Escolha de imagens"
    ),
    CHECKBOX("Escolha múltipla"), YESNO("Sim ou não")
}

data class BriefingQuestionSelection(
    val question: BriefingQuestion,
    val isSelected: Boolean
)

fun BriefingQuestion.toBriefingQuestionSelection() = BriefingQuestionSelection(
    question = this,
    isSelected = true
)

fun List<BriefingQuestion>.toBriefingQuestionsSelection() = this.map {
    it.toBriefingQuestionSelection()
}

fun BriefingQuestion.toQuestionRequest() = QuestionRequest(
    questionType = questionType.toQuestionTypeResponse(),
    caput = label,
    options = options?.mapIndexed { index, s -> QuestionOption(s, optionsUrl?.getOrNull(index)) },
    placeholder = placeholder,
    trailingText = trailingText
)

fun QuestionType.toQuestionTypeResponse() = when (this) {
    QuestionType.TEXT -> QuestionTypeResponse.TEXT
    QuestionType.NUMBER -> QuestionTypeResponse.NUMBER
    QuestionType.CURRENCY -> QuestionTypeResponse.CURRENCY
    QuestionType.RADIO -> QuestionTypeResponse.SINGLE
    QuestionType.CHECKBOX -> QuestionTypeResponse.MULTIPLE
    QuestionType.RADIOIMAGE -> QuestionTypeResponse.SINGLE
    QuestionType.YESNO -> QuestionTypeResponse.YESNO
}

