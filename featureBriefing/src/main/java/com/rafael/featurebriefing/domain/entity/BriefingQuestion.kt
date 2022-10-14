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