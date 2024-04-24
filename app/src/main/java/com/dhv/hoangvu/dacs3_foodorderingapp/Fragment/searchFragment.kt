package com.dhv.hoangvu.dacs3_foodorderingapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhv.hoangvu.dacs3_foodorderingapp.R
import com.dhv.hoangvu.dacs3_foodorderingapp.adapter.MenuAdapter
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.FragmentSearchBinding


class searchFragment : Fragment() {
    private lateinit var adapter: MenuAdapter
    private lateinit var binding: FragmentSearchBinding
    private val orignalMenuFoodName = listOf(
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
    private val orrignalMenuItemPrice =
        listOf("10$", "20$", "15$", "10$", "5$", "10$", "20$", "15$", "10$", "5$")
    private val orrignalMenuFoodImages =
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

    private val filterMenuFoodName =
        mutableListOf<String>() // danh sách lưu trữ các món ăn sau khi lọc tìm kiếm
    private val filterMenuItemPrice = mutableListOf<String>()
    private val filterMenuFoodImages = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        adapter = MenuAdapter(
            filterMenuFoodName,
            filterMenuItemPrice,
            filterMenuFoodImages,
            requireContext()
        )// MenuAdapter được khởi tạo với các dữ liệu rỗng
        binding.menuRecylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecylerView.adapter = adapter

        setupSearchView()
        showAllMenu()
        return binding.root
    }

    private fun showAllMenu() {
        filterMenuFoodName.clear()
        filterMenuItemPrice.clear()
        filterMenuFoodImages.clear()//xoá các danh sách tìm kiếm trước đó

        filterMenuFoodName.addAll(orignalMenuFoodName)//thêm tất cả các món ăn từ danh sách đầu vào danh sách lọc
        filterMenuItemPrice.addAll(orrignalMenuItemPrice)
        filterMenuFoodImages.addAll(orrignalMenuFoodImages)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String?) {
        filterMenuFoodName.clear()
        filterMenuItemPrice.clear()
        filterMenuFoodImages.clear()

        orignalMenuFoodName.forEachIndexed { index, foodName ->
            if (foodName.contains(query.toString(), ignoreCase = true)) {
                filterMenuFoodName.add(foodName)
                filterMenuItemPrice.add(orrignalMenuItemPrice[index])
                filterMenuFoodImages.add(orrignalMenuFoodImages[index])
            }
        }
        adapter.notifyDataSetChanged()// cập nhật lại danh sách món ăn trên recyler
    }

    companion object {

    }
}