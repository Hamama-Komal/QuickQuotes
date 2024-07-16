package com.example.psychebytes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psychebytes.R
import com.example.psychebytes.adapters.PopularAdapter
import com.example.psychebytes.databinding.FragmentPopularBinding
import com.example.psychebytes.models.Quote
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader


class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private lateinit var popularAdapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularBinding.inflate(layoutInflater, container, false)

        binding.popularRecycler.layoutManager = LinearLayoutManager(requireContext())

        val quotes = loadJSONFromRaw()
        val images = listOf(R.drawable.bg4, R.drawable.bg3, R.drawable.bg6, R.drawable.bg8,
            R.drawable.bg1, R.drawable.bg2, R.drawable.bg5, R.drawable.bg7, R.drawable.bg9)

        popularAdapter = PopularAdapter(requireContext(), quotes, images)
        binding.popularRecycler.adapter = popularAdapter

        return binding.root
    }

    private fun loadJSONFromRaw(): List<Quote> {
        val inputStream = resources.openRawResource(R.raw.popular)
        val reader = InputStreamReader(inputStream)
        val quotes: List<Quote> = Gson().fromJson(reader, object : TypeToken<List<Quote>>() {}.type)
        reader.close()
        return quotes
    }

}