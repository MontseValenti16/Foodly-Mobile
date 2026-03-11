package com.montse.apptransaccional.features.users.data.datasources.remote

import com.montse.apptransaccional.features.auth.data.datasources.remote.UserData

data class EmployeesResponse(
    val employees: List<UserData>
)
