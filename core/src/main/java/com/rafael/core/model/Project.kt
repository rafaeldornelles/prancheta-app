package com.rafael.core.model

import androidx.annotation.Keep
import java.util.Date

@Keep
data class Project(
    val id: String? = null,
    val clientName: String,
    val clientEmail: String,
    val architectId: String,
    val projectStart: Date,
    val briefing: BriefingForm,
    val isConcluded: Boolean = false,
    val feedback: String? = null
)