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

    fun oneTimeVibrate(time: Long, amplitude: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(time, amplitude))
        }
        else {
            vibrator.vibrate(time)
        }
    }

    fun repeatVibrate(pattern: LongArray) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
        }
        else {
            vibrator.vibrate(pattern, -1)
        }
    }
}