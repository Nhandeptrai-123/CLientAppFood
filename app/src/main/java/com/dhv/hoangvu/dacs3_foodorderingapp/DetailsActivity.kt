package com.dhv.hoangvu.dacs3_foodorderingapp

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.ActivityDetailsBinding

private lateinit var binding: ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private var nameFood: String? = null
    private var priceFood: String? = null
    private var moTaFood: String? = null
    private var imageFood: String? = null
    private var nguyenLieu: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nameFood = intent.getStringExtra("MenuItemName")
        moTaFood = intent.getStringExtra("MenuItemDescription")
        nguyenLieu = intent.getStringExtra("MenuItemIngredients")
        priceFood = intent.getStringExtra("MenuItemPrice")
        imageFood = intent.getStringExtra("MenuItemImage")

        with(binding){
            tvDetailFoodName.text= nameFood
            tvMoTa.text= moTaFood
            tvNguyenLieu.text= nguyenLieu
            Glide.with(this@DetailsActivity).load(Uri.parse(imageFood)).into(imgFood)
        }


        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}