package com.salsabila.idn.samaquran.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SuratResponseItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("nama_latin")
	val namaLatin: String,

	@field:SerializedName("jumlah_ayat")
	val jumlahAyat: String,

	@field:SerializedName("tempat_turun")
	val tempatTurun: String,

	@field:SerializedName("arti")
	val arti: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("audio")
	val audio: String,

	@field:SerializedName("nomor")
	val nomor: String
): Parcelable
