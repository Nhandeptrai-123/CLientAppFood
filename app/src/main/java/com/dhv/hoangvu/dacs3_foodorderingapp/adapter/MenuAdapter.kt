package com.dhv.hoangvu.dacs3_foodorderingapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhv.hoangvu.dacs3_foodorderingapp.DetailsActivity
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.MenuItemBinding
import com.dhv.hoangvu.dacs3_foodorderingapp.model.MenuItem

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val requireContext: Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    private val itemClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailsActivity(position)
                }
            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItem = menuItems[position]

            // a intent to open details activity and pass data
            val intent = Intent(requireContext, DetailsActivity::class.java).apply {
                putExtra("MenuItemName", menuItem.nameFood)
                putExtra("MenuItemImage", menuItem.imageFood)
                putExtra("MenuItemDescription", menuItem.moTaFood)
                putExtra("MenuItemIngredients", menuItem.nguyenLieu)
                putExtra("MenuItemPrice", menuItem.priceFood)
            }
            // start the details activity
            requireContext.startActivity(intent)
        }

        // Set data to recyclerview item name, proce, image
        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuFoodName.text = menuItem.nameFood
                menuPrice.text = menuItem.priceFood.toString()
                val uri = Uri.parse(menuItem.imageFood)
                Glide.with(requireContext).load(uri).into(menuImage)
            }
        }
    }

}
