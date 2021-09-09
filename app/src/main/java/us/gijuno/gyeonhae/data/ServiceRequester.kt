package us.gijuno.gyeonhae.data

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

object ServiceRequester {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://gyeonhae-server-rcbgcoutpa-du.a.run.app/recognize/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(Service::class.java)

    fun getBarcode(code: String): List<Barcode> {
        return service.getBarcode(code).execute().body() ?: throw IllegalArgumentException()
    }
}
