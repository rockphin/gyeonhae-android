package us.gijuno.gyeonhae.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("barcode/{code}")
    fun getBarcode(
        @Path("code") code: String,
    ): Call<String>
}
