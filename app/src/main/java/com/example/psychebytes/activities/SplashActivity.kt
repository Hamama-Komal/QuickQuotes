package com.example.psychebytes.activities


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.psychebytes.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler().postDelayed({
            val sharedPref = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
            val isFirstRun = sharedPref.getBoolean("isFirstRun", true)

            if (isFirstRun) {
                // If it's the first run, show the onboarding screen
                val editor = sharedPref.edit()
                editor.putBoolean("isFirstRun", false)
                editor.apply()

                startActivity(Intent(this, OnBoardingActivity::class.java))
            } else {
                // Otherwise, go directly to the main activity
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, 2000)
    }
}
