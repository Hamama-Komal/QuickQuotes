package com.example.psychebytes.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.psychebytes.R
import com.example.psychebytes.activities.ReadArticleActivity
import com.example.psychebytes.databinding.ArticleItemBinding
import com.example.psychebytes.models.Article

class ArticleAdapter(private val articles: List<Article>, private val context: Context) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(private  var binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.articleTitle
        val article: TextView = binding.articleDescription
        val imgUrl: ImageView = binding.articleImage
       // val category: TextView = view.findViewById(R.id.category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val article = articles[position]
        holder.title.text = article.title
        holder.article.text = article.article
       // holder.category.text = article.category
        Glide.with(holder.imgUrl.context).load(article.imgUrl).placeholder(R.drawable.photos).into(holder.imgUrl)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ReadArticleActivity::class.java).apply {
                putExtra("ARTICLE_TITLE", article.title)
                putExtra("ARTICLE", article.article)
                putExtra("ARTICLE_IMAGE_URL", article.imgUrl)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = articles.size
}
