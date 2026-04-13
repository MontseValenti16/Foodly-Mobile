package com.montse.apptransaccional.features.dashboard.domain.models

data class Area(
    val id: Int,
    val name: String,
    val icon: String?,
    val color: String?,
    val isActive: Boolean
)
