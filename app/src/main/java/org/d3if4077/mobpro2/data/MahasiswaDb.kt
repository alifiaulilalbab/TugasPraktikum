package org.d3if4077.mobpro2.data


import org.d3if4077.mobpro2.MahasiswaDao
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class MahasiswaDb private constructor() {

    private val database = FirebaseDatabase.getInstance().getReference(PATH)

    val dao = object : MahasiswaDao {
        override fun insertData(mahasiswa: Mahasiswa) {
            database.push().setValue(mahasiswa)
        }
        override fun getData(): LiveData<List<Mahasiswa>> {
            return MahasiswaLiveData(database)
        }


        override fun deleteData(ids: List<String>) {
            ids.forEach { database.child(it).removeValue() }
        }
    }
    companion object {
        private const val PATH = "mahasiswa"
        @Volatile
        private var INSTANCE: MahasiswaDb? = null
        @InternalCoroutinesApi
        fun getInstance(): MahasiswaDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = MahasiswaDb()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}