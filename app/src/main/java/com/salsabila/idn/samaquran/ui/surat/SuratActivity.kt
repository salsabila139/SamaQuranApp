package com.salsabila.idn.samaquran.ui.surat

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.salsabila.idn.samaquran.databinding.ActivitySuratBinding
import com.salsabila.idn.samaquran.ui.home.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Boolean.TRUE

class SuratActivity : AppCompatActivity() {

    lateinit var binding: ActivitySuratBinding
    lateinit var viewModel: SuratViewModel

    private var mMediaPlayer: MediaPlayer? = null
    private var isReady: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuratBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = null

        viewModel = ViewModelProvider(this)[SuratViewModel::class.java]

        val nomor = intent.getStringExtra(EXTRA_NOMOR)
        val latin = intent.getStringExtra(EXTRA_LATIN)
        val arti = intent.getStringExtra(EXTRA_ARTI)
        val jumlah = intent.getStringExtra(EXTRA_JUMLAH)
        val tempat = intent.getStringExtra(EXTRA_TEMPAT)
        val audio = intent.getStringExtra(EXTRA_AUDIO)

        if (audio != null) {
            init(audio)
        }

        val bundle = Bundle()
        bundle.putString(EXTRA_NOMOR, nomor)

        binding.apply {
            tvNamaSurat.text = latin
            tvTitleAudio.text = latin
            tvTotalAudio.text = "${jumlah} ayat"
            tvArtiSurat.text = arti
            tvTempatTurunSurat.text = "${tempat} : ${jumlah} ayat"
            ivBack.setOnClickListener {
                startActivity(Intent(this@SuratActivity, HomeActivity::class.java))
                finish()
            }
//            binding.ivPlayAudio.visibility = View.VISIBLE
            ivPlayAudio.setOnClickListener {
                if (!isReady){
                    mMediaPlayer?.prepareAsync()
//                    binding.ivPauseAudio.visibility = View.VISIBLE
//                    binding.ivPlayAudio.visibility = View.GONE
                } else {
                    if (mMediaPlayer?.isPlaying as Boolean) {
//                        ivPauseAudio.setOnClickListener {
                            mMediaPlayer?.pause()
//                        }
                    } else {
//                        ivPauseAudio.setOnClickListener {
                            mMediaPlayer?.start()
//                        }
                    }
                }
            }
//            ivPauseAudio.setOnClickListener {
//                if (!isReady){
//                    mMediaPlayer?.prepareAsync()
//                    binding.ivPauseAudio.visibility = View.GONE
//                    binding.ivPlayAudio.visibility = View.VISIBLE
//                } else {
//                    if (mMediaPlayer?.isPlaying as Boolean) {
//                        mMediaPlayer?.start()
//                    } else {
//                        mMediaPlayer?.pause()
//                    }
//                }
//            }

        }


        nomor?.let { viewModel.setListAyat(it)}
        viewModel.getListAyat().observe(this){
            if (it != null){
                binding.apply {
                    val ayatAdapter = AyatAdapter()
                    ayatAdapter.setList(it)
                    binding.rvAyat.adapter = ayatAdapter
                    binding.rvAyat.layoutManager = LinearLayoutManager(this@SuratActivity)
                }
            }
        }

        // bookmark
        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = nomor?.let { viewModel.checkBookmark(it) }
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count == TRUE) {
                        binding.tglBookmark.isChecked = true
                        isChecked = true
                    } else {
                        binding.tglBookmark.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.tglBookmark.setOnClickListener {
            isChecked = !isChecked
            if (isChecked){
                if (nomor != null && latin != null && arti != null && jumlah != null && tempat != null && audio != null) {
                    viewModel.addBookmark(
                        namaLatin = latin,
                        arti = arti,
                        jumlahAyat = jumlah,
                        tempatTurun = tempat,
                        audio = audio,
                        nomor = nomor
                    )
                }
            } else {
                if (nomor != null) {
                    viewModel.deleteBookmark(nomor)
                }
            }
            binding.tglBookmark.isChecked = isChecked
        }
    }

    private fun init(uri: String) {
        mMediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        mMediaPlayer?.setAudioAttributes(attribute)

        try{
            mMediaPlayer?.setDataSource(uri)
        } catch (e: IOException){
            e.printStackTrace()
        }
        mMediaPlayer?.setOnPreparedListener {
            isReady = true
            mMediaPlayer?.start()
        }
        mMediaPlayer?.setOnErrorListener { _, _, _ -> false }
    }

    companion object {
        const val EXTRA_NOMOR = "extra_nomor"
        const val EXTRA_LATIN = "extra_latin"
        const val EXTRA_ARTI = "extra_arti"
        const val EXTRA_JUMLAH = "extra_jumlah"
        const val EXTRA_TEMPAT = "extra_tempat"
        const val EXTRA_AUDIO = "extra_audio"
    }
}