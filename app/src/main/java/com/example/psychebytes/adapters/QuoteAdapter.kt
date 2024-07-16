import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.psychebytes.R
import com.example.psychebytes.activities.FullViewActivity
import com.example.psychebytes.databinding.CategoryQuoteItemBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

class QuoteAdapter(
    private val context: Context,
    private val quotes: List<String>
) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    private val backgrounds = listOf(
        R.drawable.bg1,
        R.drawable.bg2,
        R.drawable.bg3,
        R.drawable.bg4,
        R.drawable.bg5,
        R.drawable.bg6,
        R.drawable.bg7,
        R.drawable.bg8,
        R.drawable.bg9
        // Add more drawable resources as needed
    )
    private var currentBackgroundIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = CategoryQuoteItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return QuoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotes[position]
        holder.bind(quote)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    inner class QuoteViewHolder(private val binding: CategoryQuoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val quoteText: TextView = binding.quoteText
        private val quoteImg: ImageView = binding.quoteImg
        private val quotesLayout: ConstraintLayout = binding.quotesLayout
        private val btnBackground: LinearLayout = binding.btnBackground
        private val btnCopy: LinearLayout = binding.btnCopy
        private val btnDownload: LinearLayout = binding.btnDownload
        private val btnBookmark: LinearLayout = binding.btnBookmark
        private val btnShare: LinearLayout = binding.btnShare

        fun bind(quote: String) {
            quoteText.text = quote
            Glide.with(context).load(R.drawable.bg3).into(quoteImg) // Set default background

            btnBackground.setOnClickListener {
                currentBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.size
                quoteImg.setImageResource(backgrounds[currentBackgroundIndex])
            }

            btnCopy.setOnClickListener {
                val clipboard = getSystemService(context, ClipboardManager::class.java)
                val clip = ClipData.newPlainText("quote", quoteText.text)
                clipboard?.setPrimaryClip(clip)
                Toast.makeText(context, "Quote copied to clipboard", Toast.LENGTH_SHORT).show()
            }

            btnDownload.setOnClickListener {
                val bitmap = getBitmapFromView(quotesLayout)
                saveImageToGallery(bitmap)
            }

            btnBookmark.setOnClickListener {
                Toast.makeText(context, "Quote bookmarked", Toast.LENGTH_SHORT).show()
            }

            btnShare.setOnClickListener {
                val bitmap = getBitmapFromView(quotesLayout)
                shareImage(bitmap)
            }

            quotesLayout.setOnClickListener {
                val intent = Intent(context, FullViewActivity::class.java)
                intent.putExtra("QUOTE_TEXT", quote)
                intent.putExtra("BACKGROUND_ID", backgrounds[currentBackgroundIndex])
                context.startActivity(intent)
            }
        }

        private fun getBitmapFromView(view: View): Bitmap {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }

        private fun saveImageToGallery(bitmap: Bitmap) {
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Quote_${UUID.randomUUID()}.png")
            try {
                val fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.flush()
                fos.close()
                Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "Error saving image", Toast.LENGTH_SHORT).show()
            }
        }

        private fun shareImage(bitmap: Bitmap) {
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Quote_${UUID.randomUUID()}.png")
            try {
                val fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.flush()
                fos.close()
                val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_STREAM, uri)
                    type = "image/png"
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share image via"))
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "Error sharing image", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
