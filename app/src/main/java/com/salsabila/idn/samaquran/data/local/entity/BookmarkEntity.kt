package com.salsabila.idn.samaquran.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "bookmark")
class BookmarkEntity(
    @field:ColumnInfo(name = "nama_latin")
    val namaLatin: String,

    @field:ColumnInfo(name = "jumlah_ayat")
    val jumlahAyat: String,

    @field:ColumnInfo(name = "tempat_turun")
    val tempatTurun: String,

    @field:ColumnInfo(name = "arti")
    val arti: String,

    @field:ColumnInfo(name = "audio")
    val audio: String,

    @field:ColumnInfo(name = "nomor")
    @PrimaryKey()
    val nomor: String

)