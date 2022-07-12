package com.salsabila.idn.samaquran.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.salsabila.idn.samaquran.data.local.entity.BookmarkEntity

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM bookmark")
    fun getAllBookmark(): LiveData<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBookmark(bookmark: BookmarkEntity)

    @Query("SELECT count(*) FROM bookmark WHERE bookmark.nomor = :nomor")
    suspend fun checkBookmark(nomor: String): Boolean

    @Query("DELETE FROM bookmark WHERE bookmark.nomor = :nomor")
    suspend fun deleteBookmark(nomor: String): Int
}