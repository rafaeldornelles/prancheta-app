package com.rafael.featurebriefing.domain.entity

import androidx.annotation.Keep
import java.util.*

@Keep
data class Project(
    val id: String? = null,
    val clientName: String,
    val clientEmail: String,
    val projectStart: Date,
    val briefing: BriefingForm
)