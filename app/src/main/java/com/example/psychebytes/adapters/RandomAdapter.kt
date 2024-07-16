package com.example.psychebytes.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.bumptech.glide.request.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.example.psychebytes.R
import com.example.psychebytes.databinding.RandomItemsBinding
import com.example.psychebytes.models.Image
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RandomAdapter(
    private val context: Context,
    private val images: List<Image>
) : RecyclerView.Adapter<RandomAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = RandomItemsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        Glide.with(context)
            .load(image.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.photos)
            .into(holder.imageView)


        holder.downloadButton.setOnClickListener {
            downloadImage(image.imageUrl)
        }

        holder.bookmarkButton.setOnClickListener {
            Toast.makeText(context, "Bookmarked!", Toast.LENGTH_SHORT).show()
        }

        holder.shareButton.setOnClickListener {
            shareImage(image.imageUrl)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ImageViewHolder(private val binding: RandomItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.quoteImg
        val downloadButton = binding.btnDownload
        val bookmarkButton = binding.btnBookmark
        val shareButton = binding.btnShare
    }

    private fun downloadImage(url: String) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    saveImageToGallery(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
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


    private fun shareImage(url: String) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val file = saveImageForShare(resource)
                    if (file != null) {
                        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "image/*"
                            putExtra(Intent.EXTRA_STREAM, uri)
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
                    } else {
                        Toast.makeText(context, "Failed to share image!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun saveImageForShare(bitmap: Bitmap): File? {
        val filename = "${System.currentTimeMillis()}.jpg"
        val file = File(context.cacheDir, filename)
        return try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
