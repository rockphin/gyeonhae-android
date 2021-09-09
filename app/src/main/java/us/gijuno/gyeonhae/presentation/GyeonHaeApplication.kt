package us.gijuno.gyeonhae.presentation

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class GyeonHaeApplication : Application() {
    private var _vibrator: Vibrator? = null
    private val vibrator: Vibrator
        get() {
            if (_vibrator == null) {
                _vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            }
            return _vibrator!!
        }

    fun vibrateOnce(time: Long, amplitude: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(time, amplitude))
        } else {
            vibrator.vibrate(time)
        }
    }

    fun vibrateRepeat(pattern: LongArray) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
            vibrator.vibrate(pattern, -1)
        }
    }

    fun scanStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, 255))
        } else {
            vibrator.vibrate(500)
        }
    }

    fun scanSuccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(1000, 255))
        } else {
            vibrator.vibrate(1000)
        }
    }

    fun scanFail() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0,300,100,300,100,300), -1))
        } else {
            vibrator.vibrate(500)
        }
    }
}
