package com.rafael.featurebriefing.domain.entity

data class BriefingForm (
    val clientName: String,
    val clientEmail: String,
    val questions: List<BriefingFormQuestion>
)

data class BriefingFormQuestion(
    val question: BriefingQuestion,
    val answer: String? = null
)