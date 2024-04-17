package com.dhv.hoangvu.dacs3_foodorderingapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhv.hoangvu.dacs3_foodorderingapp.R
import com.dhv.hoangvu.dacs3_foodorderingapp.adapter.MenuAdapter
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)


        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        val menuFoodName = listOf(
            "Burger",
            "Pizza",
            "Pasta",
            "Noodles",
            "Sandwich",
            "Burger",
            "Pizza",
            "Pasta",
            "Noodles",
            "Sandwich"
        )
        val menuItemPrice =
            listOf("10$", "20$", "15$", "10$", "5$", "10$", "20$", "15$", "10$", "5$")
        val menuFoodImages =
            listOf(
                R.drawable.photo1,
                R.drawable.photo2,
                R.drawable.photo3,
                R.drawable.photo1,
                R.drawable.photo2,
                R.drawable.photo3,
                R.drawable.photo1,
                R.drawable.photo2,
                R.drawable.photo3,
                R.drawable.photo1
            )

        val adapter = MenuAdapter(
            ArrayList(menuFoodName),
            ArrayList(menuItemPrice),
            ArrayList(menuFoodImages)
        )
        binding.menuRecylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecylerView.adapter = adapter

        return binding.root
    }

    companion object {

    }
}