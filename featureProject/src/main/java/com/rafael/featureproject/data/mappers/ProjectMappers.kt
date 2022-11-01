package com.rafael.featureproject.data.mappers

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.rafael.core.model.mappers.Project

fun Project(doc: QueryDocumentSnapshot) = Project(
    id = doc.id,
    map = doc.data
)