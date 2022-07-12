package com.salsabila.idn.samaquran.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.salsabila.idn.samaquran.data.local.entity.BookmarkEntity

//cara nyambungin database ke tabel bookmark
@Database(entities = [BookmarkEntity::class], version = 1)
abstract class SuratDatabase: RoomDatabase(){

    abstract fun bookmarkDao(): BookmarkDao

    companion object{
        @Volatile
        var INSTANCE: SuratDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): SuratDatabase?{
            if (INSTANCE == null){
                synchronized(SuratDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, SuratDatabase::class.java,"surat_database").build()
                }
            }
            return INSTANCE
        }
    }
}