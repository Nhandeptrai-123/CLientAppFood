package com.dhv.hoangvu.dacs3_foodorderingapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhv.hoangvu.dacs3_foodorderingapp.adapter.MenuAdapter
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.FragmentSearchBinding
import com.dhv.hoangvu.dacs3_foodorderingapp.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class searchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var database: FirebaseDatabase
    private val originalMenuItems = mutableListOf<MenuItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        //retrieve menu item from database
        retrieveMenuItems()
        // Set up for Search view
        setupSearchView()
        return binding.root
    }

    private fun retrieveMenuItems() {
        // get database reference
        database = FirebaseDatabase.getInstance()
        // reference to the menu node
        val foodReference: DatabaseReference = database.reference.child("Food")
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let{
                        originalMenuItems.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showAllMenu() {
        val filteredMenuItem = ArrayList(originalMenuItems)
        setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filteredMenuItem: List<MenuItem>) {
        adapter = MenuAdapter(filteredMenuItem, requireContext())
        binding.menuRecylerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecylerView.adapter = adapter
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
        val filteredMenuItem = originalMenuItems.filter{
            it.nameFood?.contains(query.toString(), ignoreCase = true) == true
        }
        setAdapter(filteredMenuItem)
    }
    companion object {

    }
}