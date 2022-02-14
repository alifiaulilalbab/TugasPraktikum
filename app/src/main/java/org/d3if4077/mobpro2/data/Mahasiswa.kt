package org.d3if4077.mobpro2.data

import com.google.firebase.database.Exclude


data class Mahasiswa(
        @get:Exclude
        var id: String = "",
    val nim: String = "",
    val nama: String = ""
)
