package org.d3if4077.mobpro2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import org.d3if4077.mobpro2.data.Mahasiswa

@Dao
interface MahasiswaDao {
    @Insert
    fun insertData(mahasiswa: Mahasiswa)

    @Query("SELECT * FROM mahasiswa WHERE kelas = :kelas ORDER BY nim")
    fun getData(kelas: String): LiveData<List<Mahasiswa>>

    @Query("DELETE FROM mahasiswa WHERE id IN (:ids)")
    fun deleteData(ids: List<Int>)
}