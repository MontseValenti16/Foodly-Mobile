package com.montse.apptransaccional.features.dashboard.data.datasources.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.montse.apptransaccional.features.dashboard.domain.models.Dish

@Entity(tableName = "dishes")
data class DishEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String?,
    val price: Double,
    val categoryName: String?,
    val isAvailable: Boolean,
    val imageUrl: String?,
    val areaId: Int?,
    val categoryId: Int?
)

fun DishEntity.toDomain() = Dish(
    id = id,
    nombre = name,
    descripcion = description,
    precio = price,
    categoria = categoryName,
    disponible = isAvailable,
    imageUrl = imageUrl,
    areaId = areaId,
    categoryId = categoryId
)

fun Dish.toEntity() = DishEntity(
    id = id,
    name = nombre,
    description = descripcion,
    price = precio,
    categoryName = categoria,
    isAvailable = disponible,
    imageUrl = imageUrl,
    areaId = areaId,
    categoryId = categoryId
)
