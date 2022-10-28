package com.rafael.featurebriefing.domain.entity

data class BriefingForm (
    val id: String? = null,
    val clientName: String,
    val clientEmail: String,
    val architectId: String,
    val questions: List<BriefingFormQuestion>,
    val answerTime: Long? = null
)

data class BriefingFormQuestion(
    val question: BriefingQuestion,
    val answer: String? = null
)