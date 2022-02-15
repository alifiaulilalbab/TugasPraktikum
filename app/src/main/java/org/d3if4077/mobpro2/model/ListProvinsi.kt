package org.d3if4077.mobpro2.model

import com.squareup.moshi.Json

data class ListProvinsi(
        @Json(name = "list_data") val data: List<Provinsi>
)
