package com.rafael.featurebriefing.data.mappers

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.rafael.core.model.mappers.BriefingForm
import com.rafael.core.model.mappers.BriefingQuestion

fun BriefingForm(
    doc: QueryDocumentSnapshot
) = BriefingForm(
    id = doc.id,
    map = doc.data
)

fun BriefingForm(
    doc: DocumentSnapshot
) = doc.data?.let {
    BriefingForm(
        id = doc.id,
        map = it
    )
}

fun BriefingQuestion(
    doc: QueryDocumentSnapshot
) = BriefingQuestion(
    id = doc.id,
    map = doc.data
)