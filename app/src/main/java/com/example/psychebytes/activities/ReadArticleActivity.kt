package com.example.psychebytes.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.psychebytes.R
import com.example.psychebytes.databinding.ActivityReadArticleBinding

class ReadArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadArticleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReadArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val title = intent.getStringExtra("ARTICLE_TITLE")
        val article = intent.getStringExtra("ARTICLE")
        val imageUrl = intent.getStringExtra("ARTICLE_IMAGE_URL")

        binding.articleTitle.text = title
        binding.article.text = article
        Glide.with(this).load(imageUrl).into(binding.articleImage)
    }
}