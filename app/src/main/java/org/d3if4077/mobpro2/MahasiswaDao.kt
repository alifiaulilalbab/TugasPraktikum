package org.d3if4077.mobpro2

import androidx.lifecycle.LiveData
import org.d3if4077.mobpro2.data.Mahasiswa


interface MahasiswaDao {

    fun insertData(mahasiswa: Mahasiswa)

    fun getData(): LiveData<List<Mahasiswa>>

    fun deleteData(ids: List<String>)
}