package com.montse.apptransaccional.features.dashboard.data.repositories

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.montse.apptransaccional.features.dashboard.domain.repositories.VibrationRepository

class VibrationRepositoryImpl(private val context: Context) : VibrationRepository {
    
    private val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun vibrate(duration: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }

    override fun vibratePattern(timings: LongArray, amplitudes: IntArray, repeat: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = if (amplitudes.isNotEmpty()) {
                VibrationEffect.createWaveform(timings, amplitudes, repeat)
            } else {
                VibrationEffect.createWaveform(timings, repeat)
            }
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(timings, repeat)
        }
    }
}
