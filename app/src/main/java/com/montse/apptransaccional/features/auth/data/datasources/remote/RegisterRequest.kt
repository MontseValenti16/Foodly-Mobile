package com.montse.apptransaccional.features.auth.data.datasources.remote

data class RegisterRequest(
	val hotelID: Int = 1,
	val email: String,
	val password: String,
	val username: String,
	val rol: String = "admin_global",
	val activo: Int = 1,
	val personaID: Int = 1
)