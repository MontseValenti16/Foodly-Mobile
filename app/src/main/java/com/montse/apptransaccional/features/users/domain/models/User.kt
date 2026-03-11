package com.montse.apptransaccional.features.users.domain.models

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String? = null,
    val isActive: Boolean,
    val role: String,
    val areaId: Int? = null,
    val areaName: String? = null,
    val areaIcon: String? = null,
    val areaColor: String? = null
)
