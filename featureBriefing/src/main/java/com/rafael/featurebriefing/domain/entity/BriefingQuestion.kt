package com.rafael.featurebriefing.domain.entity

data class BriefingQuestion(
    val id: String,
    val questionType: QuestionType,
    val label: String,
    val order: Int
)

enum class QuestionType {
    TEXT
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