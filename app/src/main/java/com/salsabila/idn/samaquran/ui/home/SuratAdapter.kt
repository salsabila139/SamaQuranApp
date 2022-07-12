package com.salsabila.idn.samaquran.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salsabila.idn.samaquran.data.remote.response.SuratResponseItem
import com.salsabila.idn.samaquran.databinding.ItemSurahBinding

class SuratAdapter: RecyclerView.Adapter<SuratAdapter.SuratViewHolder>() {

    private val listSurat = ArrayList<SuratResponseItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(surats: List<SuratResponseItem>) {
        listSurat.clear()
        listSurat.addAll(surats)
        notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(surat: SuratResponseItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class SuratViewHolder(private val binding: ItemSurahBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(surat: SuratResponseItem){

            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(surat)
            }

            binding.apply {
                tvNomorSurat.text = surat.nomor
                tvNamaSurat.text = surat.namaLatin
                tvArtiSurat.text = surat.arti
                tvJumlahAyat.text = "${surat.jumlahAyat} ayat"
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuratViewHolder {
        val view = ItemSurahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuratViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuratViewHolder, position: Int) {
        holder.bind(listSurat[position])
    }

    override fun getItemCount(): Int = listSurat.size

}