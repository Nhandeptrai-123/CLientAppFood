package com.dhv.hoangvu.dacs3_foodorderingapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.ActivityDetailsBinding
import com.dhv.hoangvu.dacs3_foodorderingapp.model.GioHangItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

private lateinit var binding: ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private var nameFood: String? = null
    private var priceFood: Int = 0
    private var moTaFood: String? = null
    private var imageFood: String? = null
    private var nguyenLieu: String? = null

    private lateinit var auth: FirebaseAuth


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
        priceFood = intent.getIntExtra("MenuItemPrice", 0)
        imageFood = intent.getStringExtra("MenuItemImage")

        auth = FirebaseAuth.getInstance()

        with(binding) {
            tvDetailFoodName.text = nameFood
            tvMoTa.text = moTaFood
            tvNguyenLieu.text = nguyenLieu
            Glide.with(this@DetailsActivity).load(Uri.parse(imageFood)).into(imgFood)
        }


        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.addItemButton.setOnClickListener {
            setThemGioHang()
        }

    }


    private fun setThemGioHang() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid ?: ""

//        val price: Int = priceFood?.toIntOrNull() ?: 0
//        val foodprice: Int  = price.toInt()

        var giohangitem = GioHangItem(
            nameFood.toString(),
            priceFood.toString(),
            moTaFood.toString(),
            imageFood.toString(),
            1
        )

        database.child("ClientUser").child(userId).child("GioHang").push().setValue(giohangitem)
            .addOnCompleteListener {
                Toast.makeText(this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Không thể thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()
            }
    }


}


