package us.gijuno.gyeonhae.data

import android.app.Application
import android.util.Log
import us.gijuno.gyeonhae.presentation.GyeonHaeApplication
import java.lang.Exception

class Repository {
    var barcodeResult: String = ""
    fun getBarcode(code: String) {
        try {
            ServiceRequester.getBarcode(code).let {
                barcodeResult = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
            barcodeResult = e.toString()
            Log.d("Repository", "Barcode Result: $e")
        }
    }
}