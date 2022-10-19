package com.rafael.featurebriefing.domain.entity

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

enum class QuestionType {
    TEXT, NUMBER, CURRENCY, RADIO, RADIOIMAGE, CHECKBOX
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