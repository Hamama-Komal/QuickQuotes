package com.example.psychebytes.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.psychebytes.R
import com.example.psychebytes.activities.CategoryQuotesActivity
import com.example.psychebytes.adapters.CategoryAdapter
import com.example.psychebytes.databinding.FragmentCategoryBinding
import com.example.psychebytes.models.Category
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val categories = mutableListOf<Category>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter(requireContext(), categories) { category ->
            val intent = Intent(requireContext(), CategoryQuotesActivity::class.java).apply {
                putExtra("category_key", category.key)
                putExtra("category_name", category.name)
                putStringArrayListExtra("quotes_list", ArrayList(category.quotes))
            }
            startActivity(intent)
        }
        binding.catRecycler.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categoryAdapter
        }

        loadCategoriesFromJson()
    }

    private fun parseJson(json: String) {
        val jsonArray = JSONArray(json)
        for (i in 0 until jsonArray.length()) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
            val imgUrl = jsonObject.getString("imgUrl")
            val name = jsonObject.getString("name")
            val key = jsonObject.getString("key")
            val quotesJsonArray = jsonObject.getJSONArray("quotes")
            val quotes = mutableListOf<String>()
            for (j in 0 until quotesJsonArray.length()) {
                quotes.add(quotesJsonArray.getString(j))
            }
            categories.add(Category(imgUrl, name, key, quotes))
        }
        categoryAdapter.notifyDataSetChanged()
    }

    private fun loadCategoriesFromJson() {
        try {
            val inputStream = resources.openRawResource(R.raw.category)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charsets.UTF_8)
            parseJson(json)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

}
