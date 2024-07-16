package com.example.psychebytes.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.psychebytes.R
import com.example.psychebytes.activities.FullViewActivity
import com.example.psychebytes.databinding.PopularItemsBinding
import com.example.psychebytes.models.Quote
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PopularAdapter(
    private val context: Context,
    private val quotes: List<Quote>,
    private val images: List<Int> // List of drawable resource IDs for images
) : RecyclerView.Adapter<PopularAdapter.QuoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = PopularItemsBinding.inflate(LayoutInflater.from(context), parent, false)
        return QuoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotes[position]
        val quoteImage = images[position % images.size]
        holder.quoteTextView.text = quote.quote
        Glide.with(context).load(quoteImage).placeholder(R.drawable.photos).into(holder.quoteImageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FullViewActivity::class.java)
            intent.putExtra("QUOTE_TEXT", quote.quote)
            intent.putExtra("BACKGROUND_ID", quoteImage)
            context.startActivity(intent)
        }

        holder.downloadButton.setOnClickListener {
            val bitmap = getBitmapFromView(holder.quote)
            if (bitmap != null) {
                saveImageToGallery(bitmap)
            }
        }

        holder.bookmarkButton.setOnClickListener {
            Toast.makeText(context, "Bookmarked!", Toast.LENGTH_SHORT).show()
        }

        holder.shareButton.setOnClickListener {
            val bitmap = getBitmapFromView(holder.quote)
            if (bitmap != null) {
                shareImage(bitmap)
            }
        }


    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    class QuoteViewHolder(private val binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        val quote: ConstraintLayout = binding.quotesLayout
        val quoteImageView: ImageView = binding.quoteImg
        val quoteTextView: TextView = binding.quoteText
        val downloadButton: LinearLayout = binding.btnDownload
        val bookmarkButton: LinearLayout = binding.btnBookmark
        val shareButton: LinearLayout = binding.btnShare
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString())
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, filename)
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()

            // Add the image to the gallery
            MediaStore.Images.Media.insertImage(context.contentResolver, file.absolutePath, file.name, file.name)
            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
            Toast.makeText(context, "Image saved to gallery!", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to save image!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareImage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        val file = File(context.cacheDir, filename)
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()

            val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, uri)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to share image!", Toast.LENGTH_SHORT).show()
        }
    }
}
