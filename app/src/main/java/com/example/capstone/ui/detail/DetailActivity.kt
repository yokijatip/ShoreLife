package com.example.capstone.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.capstone.data.di.Injection
import com.example.capstone.data.retrofit.ApiService
import com.example.capstone.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = Injection.proviceApiService()

        val repository = Injection.provideRepository(this@DetailActivity)
        val factory = ViewModelFactory(repository)
        detailViewModel = ViewModelProvider(this@DetailActivity, factory)[DetailViewModel::class.java]

        getDetail()
        getDetailId()

    }

    private fun getDetail() {
        detailViewModel.itemDetail.observe(this@DetailActivity) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.photoUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(photoDetail)
                }
                binding.namaDetail.text = it.batikName
                binding.ilmiahDetail.text = it.asalDaerah
                binding.deskripsiDetail.text = it.content
            }
        }
    }

    private fun getDetailId() {
        val id = intent.getIntExtra("extra_id", -1)
        detailViewModel.getDetail(id)
    }

}