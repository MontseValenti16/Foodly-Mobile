package com.montse.apptransaccional.features.dashboard.domain.usecases

import android.net.Uri
import com.montse.apptransaccional.features.dashboard.domain.repositories.CameraRepository
import javax.inject.Inject

class CreateTempImageUriUseCase @Inject constructor(
    private val repository: CameraRepository
) {
    operator fun invoke(): Uri? = repository.createTempImageUri()
}
