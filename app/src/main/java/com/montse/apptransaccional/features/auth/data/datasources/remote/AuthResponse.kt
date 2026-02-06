package com.montse.apptransaccional.features.auth.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class AuthResponse(
	val token: String,
	@SerializedName("userID") val userId: Int,
	val message: String? = null,
	val userRol: String? = null
)