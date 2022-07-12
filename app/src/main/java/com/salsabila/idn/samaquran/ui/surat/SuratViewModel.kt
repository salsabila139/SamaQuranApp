package com.salsabila.idn.samaquran.ui.surat

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.salsabila.idn.samaquran.data.local.entity.BookmarkEntity
import com.salsabila.idn.samaquran.data.local.room.BookmarkDao
import com.salsabila.idn.samaquran.data.local.room.SuratDatabase
import com.salsabila.idn.samaquran.data.remote.response.AyatItem
import com.salsabila.idn.samaquran.data.remote.response.DetailResponse
import com.salsabila.idn.samaquran.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SuratViewModel(application : Application): AndroidViewModel(application) {

    val listAyat = MutableLiveData<List<AyatItem>>()

    private var bookmarkDao: BookmarkDao?
    private var suratDatabase: SuratDatabase? = SuratDatabase.getDatabase(application)

    init {
        bookmarkDao = suratDatabase?.bookmarkDao()
    }

    fun setListAyat(nomor: String){
        ApiConfig.getApiService()
            .getDetailSurat(nomor)
            .enqueue(object : Callback<DetailResponse>{
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful){
                        listAyat.postValue(response.body()?.ayat)
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getListAyat(): LiveData<List<AyatItem>>{
        return listAyat
    }

    fun addBookmark(namaLatin: String, jumlahAyat: String, tempatTurun: String, arti: String, audio: String, nomor: String){
        CoroutineScope(Dispatchers.IO).launch {
            val bookmark = BookmarkEntity(
                namaLatin = namaLatin,
                jumlahAyat = jumlahAyat,
                tempatTurun = tempatTurun,
                arti = arti,
                audio = audio,
                nomor = nomor
            )
            bookmarkDao?.insertBookmark(bookmark)
        }
    }

    suspend fun checkBookmark(nomor: String) = bookmarkDao?.checkBookmark(nomor)

    fun deleteBookmark(nomor: String){
        CoroutineScope(Dispatchers.IO).launch {
            bookmarkDao?.deleteBookmark(nomor)
        }
    }

}