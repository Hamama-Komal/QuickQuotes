package com.example.psychebytes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.psychebytes.R
import com.example.psychebytes.databinding.CategoryItemBinding
import com.example.psychebytes.models.Category

class CategoryAdapter(
    private val context: Context,
    private val categories: List<Category>,
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val coverImage: ImageView = binding.coverImg
        private val catName: TextView = binding.catName

        fun bind(category: Category) {
            catName.text = category.name
            Glide.with(context)
                .load(category.imgUrl)
                .placeholder(R.drawable.photos)
                .into(coverImage)

            // Set click listener
            binding.root.setOnClickListener {
                onItemClick(category)
            }
        }
    }
}
