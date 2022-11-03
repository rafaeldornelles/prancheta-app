package com.rafael.featureproject.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rafael.core.model.Project
import com.rafael.featureproject.data.mappers.Project
import com.rafael.featureproject.domain.repository.ProjectRepository
import kotlinx.coroutines.tasks.await

class ProjectRepositoryImpl : ProjectRepository {

    private val db = Firebase.firestore

    override suspend fun getProjects(userId: String): List<Project> {
        val docs = db.collection(PROJECT_COLLECTION).whereEqualTo(ARCHITECT_ID_KEY, userId).get().await()
        return docs.map {
            Project(it)
        }
    }

    override suspend fun getProject(projectId: String): Project? {
        val doc = db.collection(PROJECT_COLLECTION).document(projectId).get().await()
        return Project(doc)
    }

    companion object {
        const val PROJECT_COLLECTION = "projects"
        const val ARCHITECT_ID_KEY = "architectId"
    }
}