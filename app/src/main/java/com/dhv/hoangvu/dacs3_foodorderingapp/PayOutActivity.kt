package com.dhv.hoangvu.dacs3_foodorderingapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.ActivityPayOutBinding

private lateinit var binding: ActivityPayOutBinding

class PayOutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnThanhToan.setOnClickListener {
            val bottomSheetDiaglog = CongratsBottomSheet()
            bottomSheetDiaglog.show(supportFragmentManager, "CongratsBottomSheet")
        }
    }
}