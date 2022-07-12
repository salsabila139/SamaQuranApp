package com.salsabila.idn.samaquran.ui.home

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.salsabila.idn.samaquran.data.local.entity.BookmarkEntity
import com.salsabila.idn.samaquran.data.local.room.BookmarkDao
import com.salsabila.idn.samaquran.data.local.room.SuratDatabase
import com.salsabila.idn.samaquran.data.remote.response.AyatItem
import com.salsabila.idn.samaquran.data.remote.response.SuratResponseItem
import com.salsabila.idn.samaquran.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (application : Application) : AndroidViewModel(application){

    private var bookmarkDao: BookmarkDao?
    private var suratDatabase: SuratDatabase? = SuratDatabase.getDatabase(application)

    init {
        bookmarkDao = suratDatabase?.bookmarkDao()
    }

    val listSurat = MutableLiveData<List<SuratResponseItem>>()

    fun getListSurat(): LiveData<List<SuratResponseItem>>{
        ApiConfig.getApiService().getListSurat().enqueue(object :
            Callback<List<SuratResponseItem>> {
            override fun onResponse(
                call: Call<List<SuratResponseItem>>,
                response: Response<List<SuratResponseItem>>
            ) {
                if (response.isSuccessful){
                    listSurat.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<SuratResponseItem>>, t: Throwable) {
                t.message?.let { Log.d("Failure", it) }
            }
        })

        return listSurat
    }

    fun getAllBookmark(): LiveData<List<BookmarkEntity>>?{
        return bookmarkDao?.getAllBookmark()
    }
}