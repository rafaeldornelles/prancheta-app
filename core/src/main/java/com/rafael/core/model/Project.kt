package com.rafael.core.model

import androidx.annotation.Keep
import java.util.*

@Keep
data class Project(
    val id: String? = null,
    val clientName: String,
    val clientEmail: String,
    val architectId: String,
    val projectStart: Long,
    val briefing: BriefingForm,
    val isConcluded: Boolean = false,
    val feedback: String? = null
)