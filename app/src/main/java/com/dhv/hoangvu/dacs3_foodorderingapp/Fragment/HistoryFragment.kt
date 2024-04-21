package com.dhv.hoangvu.dacs3_foodorderingapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhv.hoangvu.dacs3_foodorderingapp.R
import com.dhv.hoangvu.dacs3_foodorderingapp.adapter.BuyAgainAdapter
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        val buyAgainFoodName = arrayListOf("Burger", "Pizza", "Pasta", "Burger", "Pizza", "Pasta")
        val buyAgainFoodPrice = arrayListOf("10", "20", "30", "10", "20", "30")
        val buyAgainFoodImage = arrayListOf(
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3,
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3
        )

        buyAgainAdapter = BuyAgainAdapter(buyAgainFoodName, buyAgainFoodPrice, buyAgainFoodImage)
        binding.rcvbuyAgain.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvbuyAgain.adapter = buyAgainAdapter
    }

    companion object {

    }
}