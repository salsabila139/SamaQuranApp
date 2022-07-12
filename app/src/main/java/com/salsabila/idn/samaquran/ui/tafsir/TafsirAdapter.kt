package com.salsabila.idn.samaquran.ui.tafsir

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salsabila.idn.samaquran.data.remote.response.TafsirItem
import com.salsabila.idn.samaquran.databinding.ItemTafsirBinding

class TafsirAdapter: RecyclerView.Adapter<TafsirAdapter.TafsirViewHolder>() {

    private val listTafsirAyat = ArrayList<TafsirItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(tafsirs: List<TafsirItem>) {
        listTafsirAyat.clear()
        listTafsirAyat.addAll(tafsirs)
        notifyDataSetChanged()
    }

    inner class TafsirViewHolder(private val binding: ItemTafsirBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(tafsir: TafsirItem){

            binding.apply {
                tvTafsir.text = "${tafsir.ayat}. ${tafsir.tafsir}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TafsirViewHolder {
        val view = ItemTafsirBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TafsirViewHolder(view)
    }

    override fun onBindViewHolder(holder: TafsirViewHolder, position: Int) {
        holder.bind(listTafsirAyat[position])
    }

    override fun getItemCount(): Int = listTafsirAyat.size
}