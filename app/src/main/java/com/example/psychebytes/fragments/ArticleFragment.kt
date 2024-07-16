package com.example.psychebytes.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psychebytes.R
import com.example.psychebytes.adapters.ArticleAdapter
import com.example.psychebytes.databinding.FragmentArticleBinding
import com.example.psychebytes.models.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader


class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(layoutInflater, container, false)

        val articles = loadJSONFromRaw(requireContext())
        binding.articleRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.articleRecycler.adapter = ArticleAdapter(articles, requireContext())


        return binding.root
    }

    fun loadJSONFromRaw(context: Context): List<Article> {
        val inputStream = context.resources.openRawResource(R.raw.article)
        val reader = InputStreamReader(inputStream)
        val articles: List<Article> = Gson().fromJson(reader, object : TypeToken<List<Article>>() {}.type)
        reader.close()
        return articles
    }
}