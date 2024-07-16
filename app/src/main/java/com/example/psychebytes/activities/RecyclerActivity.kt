package com.example.psychebytes.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.psychebytes.R
import com.example.psychebytes.databinding.ActivityRecyclerBinding
import com.example.psychebytes.fragments.ArticleFragment
import com.example.psychebytes.fragments.CategoryFragment
import com.example.psychebytes.fragments.PopularFragment
import com.example.psychebytes.fragments.RandomFragment

class RecyclerActivity : AppCompatActivity() {

    private val binding: ActivityRecyclerBinding by lazy{
        ActivityRecyclerBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val key = intent.getStringExtra("fragment_key")
        if (key != null) {
            loadFragment(key)
            binding.materialToolbar.setTitle("$key")
            binding.materialToolbar.titleMarginStart = 20
            // Set the back drawable as the navigation icon
            binding.materialToolbar.setNavigationIcon(R.drawable.back) // Ensure you have a drawable resource named ic_back
            // Handle the back button press
            binding.materialToolbar.setNavigationOnClickListener {
                finish()
            }
        }

    }

    private fun loadFragment(key: String) {
        val fragment: Fragment = when (key) {
            "Category" -> CategoryFragment()
            "Random" -> RandomFragment()
            "Popular" -> PopularFragment()
            "Article" -> ArticleFragment()
            else -> CategoryFragment() // default fragment
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}