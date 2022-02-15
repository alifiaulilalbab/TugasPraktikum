package org.d3if4077.mobpro2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4077.mobpro2.model.Harian
import java.util.Map

class MainViewModel  : ViewModel() {

    private val data = MutableLiveData<List<Harian>>()
    private val entries = MutableLiveData<List<Entry>>()
    private val status = MutableLiveData<ApiStatus>()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                requestData()
            }
        }
    }

    private suspend fun requestData() {
        try {
            status.postValue(ApiStatus.LOADING)
            val result = Covid19Api.service.getData()
            data.postValue(result.update.harian)
            entries.postValue(getEntry(result.update.harian))
            status.postValue(ApiStatus.SUCCESS)
        } catch (e: Exception) {
            status.postValue(ApiStatus.FAILED)
        }
    }

    private fun getEntry(data: List<Harian>): List<Entry> {
        val result = ArrayList<Entry>()
        var index = 1f
        for (harian in data) {
            result.add(Entry(index, harian.jumlahPositif.value.toFloat()))
            index += 1
        }
        return result
    }

    fun getData(): LiveData<List<Harian>> = data

    fun getEntries(): LiveData<List<Entry>> = entries

    fun getStatus(): LiveData<ApiStatus> = status {
    }
}