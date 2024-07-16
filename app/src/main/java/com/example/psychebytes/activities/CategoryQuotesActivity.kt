package com.example.psychebytes.activities

import QuoteAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.psychebytes.R
import com.example.psychebytes.databinding.ActivityCategoryQuotesBinding

class CategoryQuotesActivity : AppCompatActivity() {

    private val binding: ActivityCategoryQuotesBinding by lazy {
        ActivityCategoryQuotesBinding.inflate(layoutInflater)
    }
    private lateinit var quoteAdapter: QuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val categoryKey = intent.getStringExtra("category_key")
        val categoryName = intent.getStringExtra("category_name")
        val quotes = intent.getStringArrayListExtra("quotes_list") ?: emptyList<String>()


            binding.materialToolbar.setTitle("$categoryName")
            binding.materialToolbar.titleMarginStart = 20
            // Set the back drawable as the navigation icon
            binding.materialToolbar.setNavigationIcon(R.drawable.back) // Ensure you have a drawable resource named ic_back
            // Handle the back button press
            binding.materialToolbar.setNavigationOnClickListener {
                finish()

        }

        binding.materialToolbar.title = categoryName

        quoteAdapter = QuoteAdapter(this, quotes)
        binding.quoteRecycler.layoutManager = LinearLayoutManager(this)
        binding.quoteRecycler.adapter = quoteAdapter
    }
}
