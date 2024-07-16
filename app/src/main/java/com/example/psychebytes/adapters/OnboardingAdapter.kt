package com.example.psychebytes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.psychebytes.models.OnBoardingItem
import com.example.psychebytes.databinding.OnboardingItemBinding


class OnboardingAdapter(
    private val items: List<OnBoardingItem>
) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = OnboardingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class OnboardingViewHolder(private val binding: OnboardingItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(onboardingItem: OnBoardingItem) {
            binding.imageView.setImageResource(onboardingItem.image)
            binding.textViewTitle.text = onboardingItem.title
            binding.textViewDescription.text = onboardingItem.description
        }
    }
}
