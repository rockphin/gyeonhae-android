package us.gijuno.gyeonhae.data

import android.app.Application
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import us.gijuno.gyeonhae.presentation.GyeonHaeApplication
import java.lang.Exception

class Repository {
    var barcodeResult: String = "err"
    suspend fun getBarcode(code: String) {
        try {
            withContext(Dispatchers.IO) {
                ServiceRequester.getBarcode(code).let {
                    Log.d("camerax", "List<Barcode>: $it")
                    withContext(Dispatchers.Main) {
                        Log.d("camerax", "productName: ${it[0].productName}")
                        barcodeResult = it[0].productName
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Repository", "Barcode Result: $e")
        }
    }
}