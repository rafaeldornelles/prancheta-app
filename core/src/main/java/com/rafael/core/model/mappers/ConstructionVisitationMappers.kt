package com.rafael.core.model.mappers

import com.rafael.core.model.mappers.ConstructionVisitationKeys.DATE_KEY
import com.rafael.core.model.mappers.ConstructionVisitationKeys.IMAGES_KEY
import com.rafael.core.model.mappers.ConstructionVisitationKeys.OBSERVATION_KEY

fun ConstructionVisitation(
    id: String?,
    map: Map<String, Any>
) = com.rafael.core.model.ConstructionVisitation(
    id = id,
    date = map[DATE_KEY] as Long,
    observation = map[OBSERVATION_KEY] as String,
    images = map[IMAGES_KEY] as List<String>
)

private object ConstructionVisitationKeys {
    const val DATE_KEY = "date"
    const val OBSERVATION_KEY = "observation"
    const val IMAGES_KEY = "images"
}