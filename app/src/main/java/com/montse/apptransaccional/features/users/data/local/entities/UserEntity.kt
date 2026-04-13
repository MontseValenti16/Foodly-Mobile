package com.montse.apptransaccional.features.users.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.montse.apptransaccional.features.users.domain.models.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val email: String?,
    val isActive: Boolean,
    val role: String,
    val areaId: Int?,
    val areaName: String?,
    val areaIcon: String?,
    val areaColor: String?
)

fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    username = username,
    email = email,
    isActive = isActive,
    role = role,
    areaId = areaId,
    areaName = areaName,
    areaIcon = areaIcon,
    areaColor = areaColor
)

fun User.toEntity() = UserEntity(
    id = id,
    name = name,
    username = username,
    email = email,
    isActive = isActive,
    role = role,
    areaId = areaId,
    areaName = areaName,
    areaIcon = areaIcon,
    areaColor = areaColor
)
