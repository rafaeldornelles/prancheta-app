package com.rafael.core.datasource.endpoints

import com.rafael.core.datasource.model.ProjectRequest
import com.rafael.core.datasource.model.ProjectResponse
import com.rafael.core.datasource.model.ProjectStepRequest
import com.rafael.core.datasource.model.ProjectStepResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProjectEndpoint {
    @GET("/project")
    suspend fun getProjects(): Result<List<ProjectResponse>>

    @POST("/project/insert")
    suspend fun insertProject(@Body project: ProjectRequest): Result<ProjectResponse>

    @GET("/project/{id}")
    suspend fun findById(@Path("id") id: String): Result<ProjectResponse>

    @POST("/projectstep")
    suspend fun addStep(@Body step: ProjectStepRequest): Result<ProjectStepResponse>
}