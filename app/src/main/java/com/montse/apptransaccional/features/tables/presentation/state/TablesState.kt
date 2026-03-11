package com.montse.apptransaccional.features.tables.presentation.state

import com.montse.apptransaccional.features.tables.domain.models.Table

data class TablesState(
    val tables: List<Table> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    // Create form
    val number: String = "",
    val capacity: Int = 4,
    val numberError: String? = null,
    val shouldShowNumberError: Boolean = false,
    val isFormValid: Boolean = false,
    val isSubmitting: Boolean = false
)
