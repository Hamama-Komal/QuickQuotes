package com.example.psychebytes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.psychebytes.R
import com.example.psychebytes.databinding.MainItemBinding
import com.example.psychebytes.models.Image
import com.example.psychebytes.models.Quote

class MainAdapter(
    private val context: Context,
    private val images: List<Image>,
    private val quotes: List<Quote>, // Added quotes list for popular
    private val isPopular: Boolean // Flag to differentiate between random and popular
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (isPopular) VIEW_TYPE_POPULAR else VIEW_TYPE_RANDOM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return if (viewType == VIEW_TYPE_POPULAR) {
            val binding = MainItemBinding.inflate(inflater, parent, false)
            PopularViewHolder(binding)
        } else {
            val binding = MainItemBinding.inflate(inflater, parent, false)
            RandomViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isPopular) {
            (holder as PopularViewHolder).bind(images[position], quotes[position])
        } else {
            (holder as RandomViewHolder).bind(images[position])
        }
    }

    override fun getItemCount(): Int = images.size

    inner class PopularViewHolder(private val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = binding.imageView
        private val quoteText: TextView = binding.quoteText

        fun bind(image: Image, quote: Quote) {
            Glide.with(context)
                .load(image.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.gradient6)
                .into(imageView)
            quoteText.text = quote.quote
            quoteText.visibility = View.VISIBLE
        }
    }

    inner class RandomViewHolder(private val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView: ImageView = binding.imageView

        fun bind(image: Image) {
            Glide.with(context)
                .load(image.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.photos)
                .into(imageView)
            binding.quoteText.visibility = View.GONE
        }
    }

    companion object {
        private const val VIEW_TYPE_POPULAR = 1
        private const val VIEW_TYPE_RANDOM = 2
    }
}
