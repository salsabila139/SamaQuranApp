package com.salsabila.idn.samaquran.ui.tafsir

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salsabila.idn.samaquran.data.remote.response.TafsirItem
import com.salsabila.idn.samaquran.data.remote.response.TafsirResponse
import com.salsabila.idn.samaquran.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TafsirViewModel: ViewModel() {

    val listTafsir = MutableLiveData<List<TafsirItem>>()

    fun setListTafsir(nomor: String){
        ApiConfig.getApiService()
            .getTafsirSurat(nomor)
            .enqueue(object : Callback<TafsirResponse>{
                override fun onResponse(
                    call: Call<TafsirResponse>,
                    response: Response<TafsirResponse>
                ) {
                    if (response.isSuccessful){
                        listTafsir.postValue(response.body()?.tafsir)
                    }
                }

                override fun onFailure(call: Call<TafsirResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }

    fun getListTafsir(): LiveData<List<TafsirItem>>{
        return listTafsir
    }

}