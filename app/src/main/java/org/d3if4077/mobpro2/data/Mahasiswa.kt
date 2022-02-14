package org.d3if4077.mobpro2.data


@Entity
data class Mahasiswa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nim: String,
    val nama: String
)
