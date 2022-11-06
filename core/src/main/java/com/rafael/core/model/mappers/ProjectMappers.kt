package com.rafael.core.model.mappers

import com.rafael.core.model.mappers.Projectkeys.ARCHITECT_ID_KEY
import com.rafael.core.model.mappers.Projectkeys.BRIEFING_ID_KEY
import com.rafael.core.model.mappers.Projectkeys.CLIENT_EMAIL_KEY
import com.rafael.core.model.mappers.Projectkeys.CLIENT_NAME_KEY
import com.rafael.core.model.mappers.Projectkeys.FEEDBACK_KEY
import com.rafael.core.model.mappers.Projectkeys.IS_CONCLUDED_KEY
import com.rafael.core.model.mappers.Projectkeys.PROJECT_BRIEFING_KEY
import com.rafael.core.model.mappers.Projectkeys.PROJECT_START
import java.util.*

fun Project(
    id: String,
    map: Map<String, Any>
) = com.rafael.core.model.Project(
    id = id,
    clientName = map[CLIENT_NAME_KEY] as String,
    clientEmail = map[CLIENT_EMAIL_KEY] as String,
    architectId = map[ARCHITECT_ID_KEY] as String,
    projectStart = map[PROJECT_START] as Long,
    briefing = (map[PROJECT_BRIEFING_KEY] as Map<String, Any>).let {
        BriefingForm(it[BRIEFING_ID_KEY] as String, it)
    },
    isConcluded = (map[IS_CONCLUDED_KEY] as? Boolean) ?: false,
    feedback = map[FEEDBACK_KEY] as? String
)

private object Projectkeys {
    const val CLIENT_NAME_KEY = "clientName"
    const val CLIENT_EMAIL_KEY = "clientEmail"
    const val ARCHITECT_ID_KEY = "architectId"
    const val PROJECT_START = "projectStart"
    const val PROJECT_BRIEFING_KEY = "briefing"
    const val BRIEFING_ID_KEY = "id"
    const val IS_CONCLUDED_KEY = "concluded"
    const val FEEDBACK_KEY = "feedback"
}