package com.salsabila.idn.samaquran.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.salsabila.idn.samaquran.R
import com.salsabila.idn.samaquran.data.local.entity.BookmarkEntity
import com.salsabila.idn.samaquran.data.remote.response.SuratResponseItem
import com.salsabila.idn.samaquran.data.remote.retrofit.ApiConfig
import com.salsabila.idn.samaquran.databinding.FragmentHomeBinding
import com.salsabila.idn.samaquran.ui.surat.SuratActivity
import com.salsabila.idn.samaquran.ui.tafsir.TafsirActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var tabName: String? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: SuratAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabName = arguments?.getString(ARG_TAB)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        adapter = SuratAdapter()
        binding?.rvSurat?.adapter = adapter
        binding?.rvSurat?.layoutManager = LinearLayoutManager(context)

        if(tabName == TAB_SURAT){
            viewModel.getListSurat().observe(viewLifecycleOwner){
                if (it != null) {
                    adapter.setList(it)
                    binding?.progressBar?.visibility = View.GONE
                }
            }

            adapter.setOnItemClickCallback(object : SuratAdapter.OnItemClickCallback{
                override fun onItemClicked(surat: SuratResponseItem) {
                    Intent(activity, SuratActivity::class.java).also {
                        it.putExtra(SuratActivity.EXTRA_NOMOR, surat.nomor)
                        it.putExtra(SuratActivity.EXTRA_LATIN, surat.namaLatin)
                        it.putExtra(SuratActivity.EXTRA_ARTI, surat.arti)
                        it.putExtra(SuratActivity.EXTRA_JUMLAH, surat.jumlahAyat)
                        it.putExtra(SuratActivity.EXTRA_TEMPAT, surat.tempatTurun)
                        it.putExtra(SuratActivity.EXTRA_AUDIO, surat.audio)
                        startActivity(it)
                    }
                }

            })
        } else if(tabName == TAB_TAFSIR){
            viewModel.getListSurat().observe(viewLifecycleOwner){
                if (it != null) {
                    adapter.setList(it)
                    binding?.progressBar?.visibility = View.GONE
                }
            }

            adapter.setOnItemClickCallback(object : SuratAdapter.OnItemClickCallback{
                override fun onItemClicked(surat: SuratResponseItem) {
                    Intent(activity, TafsirActivity::class.java).also {
                        it.putExtra(TafsirActivity.EXTRA_NOMOR, surat.nomor)
                        it.putExtra(TafsirActivity.EXTRA_LATIN, surat.namaLatin)
                        it.putExtra(TafsirActivity.EXTRA_ARTI, surat.arti)
                        it.putExtra(TafsirActivity.EXTRA_JUMLAH, surat.jumlahAyat)
                        it.putExtra(TafsirActivity.EXTRA_TEMPAT, surat.tempatTurun)
                        startActivity(it)
                    }
                }
            })
        } else {
            viewModel.getAllBookmark()?.observe(viewLifecycleOwner){
                if (it != null){
                    val list = mapList(it)
                    adapter.setList(list)
                    binding?.progressBar?.visibility = View.GONE
                }
            }
        }
    }

    private fun mapList(bookmarks: List<BookmarkEntity>) : ArrayList<SuratResponseItem>{
        val listBookmark = ArrayList<SuratResponseItem>()
        for (bookmark in bookmarks){
            val bookmarkMapped = SuratResponseItem(
                nama = "",
                namaLatin = bookmark.namaLatin,
                jumlahAyat = bookmark.jumlahAyat,
                tempatTurun = bookmark.tempatTurun,
                arti = bookmark.arti,
                deskripsi = "",
                audio = bookmark.audio,
                nomor = bookmark.nomor
            )
            listBookmark.add(bookmarkMapped)
        }
        return listBookmark
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_SURAT = "surah"
        const val TAB_TAFSIR = "tafsir"
        const val TAB_BOOKMARK = "bookmark"
    }
}
