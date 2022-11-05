package com.rafael.featureproject.data.mappers

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.rafael.core.model.Project
import com.rafael.core.model.mappers.ConstructionVisitation
import com.rafael.core.model.mappers.Project

fun Project(doc: QueryDocumentSnapshot) = Project(
    id = doc.id,
    map = doc.data
)

fun Project(doc: DocumentSnapshot) = doc.data?.let {
    Project(doc.id, it)
}

fun ConstructionVisitation(doc: QueryDocumentSnapshot) = ConstructionVisitation(
    id = doc.id,
    map = doc.data
)
fun ConstructionVisitation(doc: DocumentSnapshot) = doc.data?.let {
    ConstructionVisitation(doc.id, it)
}