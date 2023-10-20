package com.rafael.core.datasource.model

data class AnswerBriefingRequest(
    val briefing: String,
    val answers: List<String>
)