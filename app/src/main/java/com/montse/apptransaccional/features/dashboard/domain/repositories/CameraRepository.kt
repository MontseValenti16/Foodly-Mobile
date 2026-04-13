package com.montse.apptransaccional.features.dashboard.domain.repositories

import android.net.Uri

interface CameraRepository {
    fun createTempImageUri(): Uri?
}
