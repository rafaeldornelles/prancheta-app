package com.rafael.featureproject.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rafael.core.model.ConstructionVisitation
import com.rafael.core.model.Project
import com.rafael.featureproject.data.mappers.ConstructionVisitation
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

    override suspend fun getVisitations(projectId: String): List<ConstructionVisitation> {
        val doc = db.collection(PROJECT_COLLECTION).document(projectId).collection(
            VISITATIONS_COLLECTION).get().await()
        return doc.map {
            ConstructionVisitation(it)
        }
    }

    override suspend fun addVisitation(projectId: String, visitation: ConstructionVisitation) {
        db.collection(PROJECT_COLLECTION).document(projectId).collection(VISITATIONS_COLLECTION).add(visitation)
    }

    override suspend fun getVisitation(projectId: String, visitationId: String): ConstructionVisitation? {
        val doc = db.collection(PROJECT_COLLECTION).document(projectId).collection(
            VISITATIONS_COLLECTION).document(visitationId).get().await()
        return ConstructionVisitation(doc)
    }

    override suspend fun updateProject(projectId: String, project: Project) {
        db.collection(PROJECT_COLLECTION).document(projectId).set(project)
    }

    companion object {
        const val PROJECT_COLLECTION = "projects"
        const val ARCHITECT_ID_KEY = "architectId"
        const val VISITATIONS_COLLECTION = "visitation"
    }
}