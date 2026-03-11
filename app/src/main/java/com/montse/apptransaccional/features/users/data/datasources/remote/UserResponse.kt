package com.montse.apptransaccional.features.users.data.datasources.remote

import com.montse.apptransaccional.features.auth.data.datasources.remote.UserData
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("employee") val employee: UserData
)
