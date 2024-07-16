package com.example.psychebytes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psychebytes.R
import com.example.psychebytes.adapters.RandomAdapter
import com.example.psychebytes.databinding.FragmentRandomBinding
import com.example.psychebytes.models.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class RandomFragment : Fragment() {

    private lateinit var binding: FragmentRandomBinding
    private lateinit var randomAdapter: RandomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentRandomBinding.inflate(layoutInflater, container, false)

        binding.randomRecycler.layoutManager = LinearLayoutManager(context)

        val images = loadJSONFromRaw()
        randomAdapter = RandomAdapter(requireContext(), images)
        binding.randomRecycler.adapter = randomAdapter
        return binding.root
    }

    private fun loadJSONFromRaw(): List<Image> {
        val inputStream = resources.openRawResource(R.raw.random)
        val reader = InputStreamReader(inputStream)
        val images: List<Image> = Gson().fromJson(reader, object : TypeToken<List<Image>>() {}.type)
        reader.close()
        return images
    }

}