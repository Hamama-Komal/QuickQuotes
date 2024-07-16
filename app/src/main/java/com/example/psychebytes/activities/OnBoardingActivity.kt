package com.example.psychebytes.activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.psychebytes.models.OnBoardingItem
import com.example.psychebytes.adapters.OnboardingAdapter
import com.example.psychebytes.R
import com.example.psychebytes.databinding.ActivityOnBoardingBinding
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val onboardingItems = listOf(
            OnBoardingItem(R.drawable.img1, "Discover Fascinating Psychology Facts", "Explore intriguing facts about the human mind and behavior."),
            OnBoardingItem(R.drawable.img2, "Daily Mind-Blowing Facts", "Receive a new psychological fact daily to keep your curiosity alive."),
            OnBoardingItem(R.drawable.img3, "Boost Your Knowledge", "Enhance your understanding of psychology with curated facts and explanations.")
        )

        val adapter = OnboardingAdapter(onboardingItems)
        binding.viewPager.adapter = adapter

        binding.btnSkip.setOnClickListener {
            navigateToHome()
        }

        binding.next.setOnClickListener {
            if (binding.viewPager.currentItem + 1 < adapter.itemCount) {
                binding.viewPager.currentItem += 1
            } else {
                navigateToHome()
            }
        }

      /*  binding.btnNext.setOnClickListener {
            if (binding.viewPager.currentItem + 1 < adapter.itemCount) {
                binding.viewPager.currentItem += 1
            } else {
                navigateToHome()
            }
        }

        // Handle the Back button
        binding.btnBack.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem > 0) {
                binding.viewPager.currentItem = currentItem - 1
            }
        }*/

      /*  // Connect TabLayout with ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->

            // You can optionally set tab text or icons here
        }.attach()*/
    }

    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
