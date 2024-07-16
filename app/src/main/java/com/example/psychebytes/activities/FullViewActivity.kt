package com.example.psychebytes.activities

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.psychebytes.R
import com.example.psychebytes.databinding.ActivityFullViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class FullViewActivity : AppCompatActivity() {

    private val binding: ActivityFullViewBinding by lazy {
        ActivityFullViewBinding.inflate(layoutInflater)
    }

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val quote = intent.getStringExtra("QUOTE_TEXT")
        val backgroundId = intent.getIntExtra("BACKGROUND_ID", 0)

        binding.quoteText.text = quote
        binding.quoteImg.setImageResource(backgroundId)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnBackground.setOnClickListener {
            currentBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.size
            binding.quoteImg.setImageResource(backgrounds[currentBackgroundIndex])
        }

        binding.btnColor.setOnClickListener {
            showColorPickerDialog()
        }

        binding.btnText.setOnClickListener {
            showTextCustomizationDialog()
        }

        binding.btnCopy.setOnClickListener {
            copyQuoteToClipboard()
        }

        binding.btnDownload.setOnClickListener {
            downloadQuoteLayoutAsImage()
        }

        binding.btnBookmark.setOnClickListener {
            Toast.makeText(this, "Quote bookmarked", Toast.LENGTH_SHORT).show()
        }

        binding.btnShare.setOnClickListener {
            shareQuoteLayoutAsImage()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showColorPickerDialog() {
        val dialog = BottomSheetDialog(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_color_picker, null)

        binding.quoteImg.setImageResource(0)


        dialogView.findViewById<TextView>(R.id.textNone).setOnClickListener {
            binding.quoteImg.setImageResource(0)
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            dialog.dismiss()
        }

        // Assuming you have some predefined colors in your XML
        dialogView.findViewById<View>(R.id.colorBlack).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorBlue).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.royal))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorMyRed).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorPurple).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.purple))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorOrange).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.orange))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorYellow).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.amber))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorGrey).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorWheat).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.wheat))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorBrown).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.brown))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorPink).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorTea).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.tea))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorMint).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.mint))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorPeach).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.peach))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorCream).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.cream))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.colorWhite).setOnClickListener {
            binding.quoteImg.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.gradiant1).setOnClickListener {
            binding.quoteImg.setImageResource(R.drawable.grediant1)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.gradiant2).setOnClickListener {
            binding.quoteImg.setImageResource(R.drawable.gradient2)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.gradiant3).setOnClickListener {
            binding.quoteImg.setImageResource(R.drawable.gradient3)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.gradiant4).setOnClickListener {
            binding.quoteImg.setImageResource(R.drawable.gradient4)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.gradiant5).setOnClickListener {
            binding.quoteImg.setImageResource(R.drawable.gradient5)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.gradiant6).setOnClickListener {
            binding.quoteImg.setImageResource(R.drawable.gradient6)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.gradiant7).setOnClickListener {
            binding.quoteImg.setImageResource(R.drawable.gradient7)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.gradiant8).setOnClickListener {
            binding.quoteImg.setImageResource(R.drawable.gradient8)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.gradiant9).setOnClickListener {
            binding.quoteImg.setImageResource(R.drawable.gradient9)
            dialog.dismiss()
        }


        // Add more colors as needed
        dialog.setContentView(dialogView)
        dialog.show()
    }


    @SuppressLint("MissingInflatedId")
    private fun showTextCustomizationDialog() {
        val dialog = BottomSheetDialog(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_text_customization, null)

        // Font size customization
        dialogView.findViewById<SeekBar>(R.id.fontSizeSeekBar)
            .setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?, progress: Int, fromUser: Boolean
                ) {
                    binding.quoteText.textSize = progress.toFloat()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

        // Font style customization
        dialogView.findViewById<Button>(R.id.fontBold).setOnClickListener {
            binding.quoteText.setTypeface(null, Typeface.BOLD)
        }
        dialogView.findViewById<Button>(R.id.fontItalic).setOnClickListener {
            binding.quoteText.setTypeface(null, Typeface.ITALIC)
        }
        dialogView.findViewById<Button>(R.id.fontNormal).setOnClickListener {
            binding.quoteText.setTypeface(null, Typeface.NORMAL)
        }

        // Font family customization
        dialogView.findViewById<Button>(R.id.fontPoppins).setOnClickListener {
            binding.quoteText.typeface = ResourcesCompat.getFont(this, R.font.poppins)
        }
        dialogView.findViewById<Button>(R.id.fontAmarante).setOnClickListener {
            binding.quoteText.typeface = ResourcesCompat.getFont(this, R.font.amarante)
        }
        dialogView.findViewById<Button>(R.id.fontBaloo).setOnClickListener {
            binding.quoteText.typeface = ResourcesCompat.getFont(this, R.font.baloo)
        }
        dialogView.findViewById<Button>(R.id.fontCharm).setOnClickListener {
            binding.quoteText.typeface = ResourcesCompat.getFont(this, R.font.charm)
        }
        dialogView.findViewById<Button>(R.id.fontChicle).setOnClickListener {
            binding.quoteText.typeface = ResourcesCompat.getFont(this, R.font.chicle)
        }
        dialogView.findViewById<Button>(R.id.fontAndika).setOnClickListener {
            binding.quoteText.typeface = ResourcesCompat.getFont(this, R.font.andika_new_basic)
        }
        dialogView.findViewById<Button>(R.id.fontEwert).setOnClickListener {
            binding.quoteText.typeface = ResourcesCompat.getFont(this, R.font.ewert)
        }
        dialogView.findViewById<Button>(R.id.fontNova).setOnClickListener {
            binding.quoteText.typeface = ResourcesCompat.getFont(this, R.font.nova_round)
        }


        // Text color customization
        dialogView.findViewById<View>(R.id.colorBlackText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
        dialogView.findViewById<View>(R.id.colorGreyText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.grey))
        }
        dialogView.findViewById<View>(R.id.colorRedText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.red))
        }
        dialogView.findViewById<View>(R.id.colorRoyalText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.royal))
        }
        dialogView.findViewById<View>(R.id.colorOrangeText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.orange))
        }
        dialogView.findViewById<View>(R.id.colorPurpleText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.purple))
        }
        dialogView.findViewById<View>(R.id.colorYellowText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.amber))
        }
        dialogView.findViewById<View>(R.id.colorBlueText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.blue))
        }
        dialogView.findViewById<View>(R.id.colorWheatText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.wheat))
        }
        dialogView.findViewById<View>(R.id.colorWhiteText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
        dialogView.findViewById<View>(R.id.colorTeaText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.tea))
        }
        dialogView.findViewById<View>(R.id.colorPinkText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.pink))
        }
        dialogView.findViewById<View>(R.id.colorPeachText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.peach))
        }
        dialogView.findViewById<View>(R.id.colorGreenText).setOnClickListener {
            binding.quoteText.setTextColor(ContextCompat.getColor(this, R.color.green1))
        }


        // Add more options for text customization as needed

        dialog.setContentView(dialogView)
        dialog.show()
    }

    private fun copyQuoteToClipboard() {
        val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java)
        val clip = ClipData.newPlainText("quote", binding.quoteText.text)
        clipboard?.setPrimaryClip(clip)
        Toast.makeText(this, "Quote copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun downloadQuoteLayoutAsImage() {
        val bitmap = getBitmapFromView(binding.quotesLayout)
        saveImageToGallery(bitmap)
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        val filename = "Quote_${UUID.randomUUID()}.png"
        var fos: OutputStream? = null

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            val resolver = contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            val imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = resolver.openOutputStream(imageUri!!)
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString()
            val file = File(imagesDir, filename)
            fos = FileOutputStream(file)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show()
        }

        // Notify the gallery about the new image
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString(), filename
            )
            val contentUri = Uri.fromFile(file)
            mediaScanIntent.data = contentUri
            this.sendBroadcast(mediaScanIntent)
        }
    }

    private fun shareQuoteLayoutAsImage() {
        val bitmap = getBitmapFromView(binding.quotesLayout)
        val file = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Quote_${UUID.randomUUID()}.png"
        )
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
            val uri = FileProvider.getUriForFile(this, "${packageName}.provider", file)
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "image/png"
            }
            startActivity(Intent.createChooser(shareIntent, "Share image via"))
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error sharing image", Toast.LENGTH_SHORT).show()
        }
    }
}
