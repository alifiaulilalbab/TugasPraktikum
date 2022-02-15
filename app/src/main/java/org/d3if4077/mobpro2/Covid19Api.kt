package org.d3if4077.mobpro2

import org.d3if4077.mobpro2.model.Data
import org.d3if4077.mobpro2.model.ListProvinsi
import retrofit2.http.GET

enum class ApiStatus { LOADING, SUCCESS, FAILED }
object Covid19Api {

    private const val BASE_URL = "https://data.covid19.go.id/public/api/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    val service: ApiService by lazy { retrofit.create(ApiService::class.java) }

    interface ApiService {
        @GET("update.json")
        suspend fun getData(): Data

        @GET("prov.json")
        suspend fun getProvinsi(): ListProvinsi
    }
}