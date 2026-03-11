package com.montse.apptransaccional.features.tables.domain.models

data class Table(
    val id: Int,
    val number: Int,
    val capacity: Int,
    val isActive: Boolean
)
