package com.salsabila.idn.samaquran.ui.surat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salsabila.idn.samaquran.data.remote.response.AyatItem
import com.salsabila.idn.samaquran.databinding.ItemAyatBinding

class AyatAdapter: RecyclerView.Adapter<AyatAdapter.AyatViewHolder>() {

    private val listAyat = ArrayList<AyatItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(ayats: List<AyatItem>) {
        listAyat.clear()
        listAyat.addAll(ayats)
        notifyDataSetChanged()
    }

    inner class AyatViewHolder(private val binding: ItemAyatBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(ayat: AyatItem){

            binding.apply {
                tvAyatArab.text = "${ayat.ar}"
                tvAyatArti.text = "${ayat.nomor}. ${ayat.idn}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatViewHolder {
        val view = ItemAyatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AyatViewHolder(view)
    }

    override fun onBindViewHolder(holder: AyatViewHolder, position: Int) {
        holder.bind(listAyat[position])
    }

    override fun getItemCount(): Int = listAyat.size
}