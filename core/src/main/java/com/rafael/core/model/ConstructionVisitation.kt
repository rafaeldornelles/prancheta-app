package com.rafael.core.model

data class ConstructionVisitation(
    val id: String?,
    val date: Long,
    val observation: String,
    val images: List<String>
)