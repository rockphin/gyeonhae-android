package us.gijuno.gyeonhae.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

data class Barcode (
    val productName: String,
        )

interface Service {
    @GET("barcode/{code}")
    fun getBarcode(
        @Path("code") code: String,
    ): Call<List<Barcode>>
}
