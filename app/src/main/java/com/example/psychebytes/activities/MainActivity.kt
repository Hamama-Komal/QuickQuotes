package com.example.psychebytes.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.psychebytes.R
import com.example.psychebytes.adapters.MainAdapter
import com.example.psychebytes.databinding.ActivityMainBinding
import com.example.psychebytes.fragments.ArticleFragment
import com.example.psychebytes.fragments.CategoryFragment
import com.example.psychebytes.models.Image
import com.example.psychebytes.models.Quote
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Image Slider
        setImageSlider()

        binding.btnCat.setOnClickListener {
            goToRecyclerActivity("Category")
        }
        binding.btnRandom.setOnClickListener {
            goToRecyclerActivity("Random")
        }
        binding.btnPopular.setOnClickListener {
            goToRecyclerActivity("Popular")
        }
        binding.btnArticle.setOnClickListener {
            goToRecyclerActivity("Article")
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragmentCategoryView, CategoryFragment::class.java, null)
            }
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragmentArticleView, ArticleFragment::class.java, null)
            }
        }

        binding.fragmentCategoryView.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentCategoryView, CategoryFragment::class.java, null)
                addToBackStack(null)
            }
        }

        binding.fragmentArticleView.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragmentCategoryView, ArticleFragment::class.java, null)
                addToBackStack(null)
            }
        }

        binding.randomText.setOnClickListener {
            goToRecyclerActivity("Random")
        }

        binding.popularText.setOnClickListener {
            goToRecyclerActivity("Popular")
        }

        binding.articleText.setOnClickListener {
            goToRecyclerActivity("Article")
        }

        // Load data with coroutines
        loadData()

        // Handle the toggle button for showing/hiding the article fragment
        handleToggleButton()
    }

    private fun handleToggleButton() {
        val toggleBtn = binding.toggleBtn
        val fragmentArticleView = binding.fragmentArticleView

        toggleBtn.setOnClickListener {
            if (toggleBtn.text == "Show") {
                toggleBtn.text = "Hide"
                fragmentArticleView.visibility = View.VISIBLE
            } else {
                toggleBtn.text = "Show"
                fragmentArticleView.visibility = View.GONE
            }
        }

        // Initially set the fragmentArticleView to GONE
        fragmentArticleView.visibility = View.GONE
    }

    private fun loadData() {
        // Load popular and random images asynchronously
        CoroutineScope(Dispatchers.IO).launch {
            val popularImages = loadJSONFromRawPopular()
            val popularQuotes = loadJSONFromRawPopularText()
            val randomImages = loadJSONFromRaw()

            withContext(Dispatchers.Main) {
                // Update UI on the main thread
                setPopularRecycler(popularImages, popularQuotes)
                setRandomRecycler(randomImages)
            }
        }
    }

    private fun setPopularRecycler(images: List<Image>, quotes: List<Quote>) {
        binding.mainPopularRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mainAdapter = MainAdapter(this, images, quotes, true)
        binding.mainPopularRecycler.adapter = mainAdapter
    }

    private fun setRandomRecycler(images: List<Image>) {
        binding.mainRandomRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mainAdapter = MainAdapter(this, images, emptyList(), false)
        binding.mainRandomRecycler.adapter = mainAdapter
    }

    private suspend fun loadJSONFromRawPopular(): List<Image> {
        return withContext(Dispatchers.IO) {
            val inputStream = resources.openRawResource(R.raw.popular)
            val reader = InputStreamReader(inputStream)
            val images: List<Image> = Gson().fromJson(reader, object : TypeToken<List<Image>>() {}.type)
            reader.close()
            images
        }
    }

    private suspend fun loadJSONFromRawPopularText(): List<Quote> {
        return withContext(Dispatchers.IO) {
            val inputStream = resources.openRawResource(R.raw.popular)
            val reader = InputStreamReader(inputStream)
            val quotes: List<Quote> = Gson().fromJson(reader, object : TypeToken<List<Quote>>() {}.type)
            reader.close()
            quotes
        }
    }

    private suspend fun loadJSONFromRaw(): List<Image> {
        return withContext(Dispatchers.IO) {
            val inputStream = resources.openRawResource(R.raw.random)
            val reader = InputStreamReader(inputStream)
            val images: List<Image> = Gson().fromJson(reader, object : TypeToken<List<Image>>() {}.type)
            reader.close()
            images
        }
    }

    private fun goToRecyclerActivity(key: String) {
        val intent = Intent(this, RecyclerActivity::class.java)
        intent.putExtra("fragment_key", key)
        startActivity(intent)
    }

    private fun setImageSlider() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://media.istockphoto.com/id/516633778/photo/inspirational-life-quote-about-life.jpg?s=612x612&w=0&k=20&c=MfKlKR9qrXvDwElxT002lWBHdUTd5h_hM2CqTMFhefg=", ScaleTypes.FIT))
        imageList.add(SlideModel("https://media.istockphoto.com/id/639223350/photo/believe-in-your-potential-on-blur-city-background.jpg?s=612x612&w=0&k=20&c=CFgJdvrg3Vp6vYb70TLkb7rlviHoPsb23cGxcgFEo3k=", ScaleTypes.FIT))
        imageList.add(SlideModel("https://media.istockphoto.com/id/869351976/vector/inspirational-motivating-quote.jpg?s=612x612&w=0&k=20&c=OEAFO0SMLkCKR_9v1TVRL-I2u_NvKJOY5DYJRL2Vz6k=", ScaleTypes.FIT))
        imageList.add(SlideModel("https://media.istockphoto.com/id/1348762633/photo/inspirational-and-motivational-concept.jpg?s=612x612&w=0&k=20&c=gKaqEjRPR4rX3Snbc6SX-TY7muhe8NZO_JRTFDnmMcI=", ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                // Handle double click
            }

            override fun onItemSelected(position: Int) {
                // Handle item selected
            }
        })
    }
}
