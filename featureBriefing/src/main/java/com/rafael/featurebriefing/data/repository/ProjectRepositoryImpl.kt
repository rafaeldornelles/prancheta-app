package com.rafael.featurebriefing.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rafael.featurebriefing.domain.entity.Project
import com.rafael.featurebriefing.domain.repository.ProjectRepository
import kotlinx.coroutines.tasks.await

class ProjectRepositoryImpl : ProjectRepository {

    private val db = Firebase.firestore

    override suspend fun startProject(project: Project): String {
        val doc = db.collection(PROJECT_COLLECTION).add(project).await()
        return doc.id
    }

    companion object {
        private const val PROJECT_COLLECTION = "projects"
    }
}