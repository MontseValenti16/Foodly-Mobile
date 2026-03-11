package com.montse.apptransaccional.features.dashboard.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class DishResponse(
    @SerializedName("product") val product: DishDto
)
