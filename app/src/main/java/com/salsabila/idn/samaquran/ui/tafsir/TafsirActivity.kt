package com.salsabila.idn.samaquran.ui.tafsir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.salsabila.idn.samaquran.R
import com.salsabila.idn.samaquran.databinding.ActivityTafsirBinding
import com.salsabila.idn.samaquran.ui.home.HomeActivity
import com.salsabila.idn.samaquran.ui.surat.SuratActivity
import com.salsabila.idn.samaquran.ui.surat.SuratViewModel

class TafsirActivity : AppCompatActivity() {

    lateinit var binding: ActivityTafsirBinding
    lateinit var viewModel: TafsirViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTafsirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = null

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[TafsirViewModel::class.java]

        val nomor = intent.getStringExtra(EXTRA_NOMOR)
        val latin = intent.getStringExtra(EXTRA_LATIN)
        val arti = intent.getStringExtra(EXTRA_ARTI)
        val jumlah = intent.getStringExtra(EXTRA_JUMLAH)
        val tempat = intent.getStringExtra(EXTRA_TEMPAT)

        val bundle = Bundle()
        bundle.putString(EXTRA_NOMOR, nomor)

        binding.apply {
            tvNamaSurat.text = latin
            tvArtiSurat.text = arti
            tvTempatTurunSurat.text = "${tempat} : ${jumlah} ayat"
            ivBack.setOnClickListener {
                startActivity(Intent(this@TafsirActivity, HomeActivity::class.java))
                finish()
            }
        }

        nomor?.let { viewModel.setListTafsir(it)}
        viewModel.getListTafsir().observe(this){
            if (it != null){
                binding.apply {
                    val tafsirAdapter = TafsirAdapter()
                    tafsirAdapter.setList(it)
                    binding.rvTafsir.adapter = tafsirAdapter
                    binding.rvTafsir.layoutManager = LinearLayoutManager(this@TafsirActivity)
                }
            }
        }

    }

    companion object {
        const val EXTRA_NOMOR = "extra_nomor"
        const val EXTRA_LATIN = "extra_latin"
        const val EXTRA_ARTI = "extra_arti"
        const val EXTRA_JUMLAH = "extra_jumlah"
        const val EXTRA_TEMPAT = "extra_tempat"
    }
}