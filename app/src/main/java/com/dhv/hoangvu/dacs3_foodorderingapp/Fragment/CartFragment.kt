package com.dhv.hoangvu.dacs3_foodorderingapp.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhv.hoangvu.dacs3_foodorderingapp.PayOutActivity
import com.dhv.hoangvu.dacs3_foodorderingapp.R
import com.dhv.hoangvu.dacs3_foodorderingapp.adapter.CartAdapter
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)


        val cartFoodname = listOf("Burger", "Pizza", "Pasta", "Burger", "Pizza", "Pasta")
        val cartItemPrice = listOf("$10", "$20", "$30", "$10", "$20", "$30")
        val cartImage = listOf(
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3,
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3
        )

        val adapter =
            CartAdapter(ArrayList(cartFoodname), ArrayList(cartItemPrice), ArrayList(cartImage))
        binding.cartRevylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRevylerView.adapter = adapter


        binding.proceedButton.setOnClickListener {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    companion object {

    }
}