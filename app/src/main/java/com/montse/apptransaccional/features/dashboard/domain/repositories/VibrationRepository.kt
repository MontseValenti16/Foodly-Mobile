package com.montse.apptransaccional.features.dashboard.domain.repositories

interface VibrationRepository {
    fun vibrate(duration: Long = 200)
    fun vibratePattern(timings: LongArray, amplitudes: IntArray = intArrayOf(), repeat: Int = -1)
}
