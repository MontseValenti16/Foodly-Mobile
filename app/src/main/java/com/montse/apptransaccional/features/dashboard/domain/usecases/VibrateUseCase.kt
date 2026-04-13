package com.montse.apptransaccional.features.dashboard.domain.usecases

import com.montse.apptransaccional.features.dashboard.domain.repositories.VibrationRepository
import javax.inject.Inject

class VibrateUseCase @Inject constructor(
    private val repository: VibrationRepository
) {
    operator fun invoke(duration: Long = 200) {
        repository.vibrate(duration)
    }

    fun vibrateTriple() {
        // Aumentamos la duración de las pulsaciones a 400ms y las pausas a 200ms
        val timings = longArrayOf(0, 400, 200, 400, 200, 400)
        repository.vibratePattern(timings)
    }
}
