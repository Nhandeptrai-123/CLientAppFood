package com.dhv.hoangvu.dacs3_foodorderingapp.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhv.hoangvu.dacs3_foodorderingapp.PayOutActivity
import com.dhv.hoangvu.dacs3_foodorderingapp.adapter.CartAdapter
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.FragmentCartBinding
import com.dhv.hoangvu.dacs3_foodorderingapp.model.GioHangItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>
    private lateinit var foodImagesUri: MutableList<String>
    private lateinit var foodIngredients: MutableList<String>
    private lateinit var quatitys: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        // Khởi tạo database trước khi gọi retrieveCartItems()
        database = FirebaseDatabase.getInstance()
        retrieveCartItems()


        binding.proceedButton.setOnClickListener {
            // lấy dữ liệu các món ăn trước khi check out processing
            getOrderItemDetail()
//            val intent = Intent(requireContext(), PayOutActivity::class.java)
//            startActivity(intent)
        }



        return binding.root
    }


    private fun getOrderItemDetail() {
        val orderIdReference: DatabaseReference =
            database.reference.child("ClientUser").child(userId).child("GioHang")

        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngredient = mutableListOf<String>()

        //lấy dữ liệu số lượng món ăn
        val foodQuantities = cartAdapter.getUpdateItemsQuamtities()

        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    // đưa mục vào danh sách tương ứng
                    val orderItems = foodSnapshot.getValue(GioHangItem::class.java)
                    //thêm item vào danh sách
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
//                    orderItems?.foodIngredients?.let{foodName.add(it)}
                }
                orderNow(foodName, foodPrice, foodDescription, foodImage, foodQuantities)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Đặt hàng lỗi. Làm ơn thử lại", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescription: MutableList<String>,
        foodImage: MutableList<String>,
        foodQuantities: MutableList<Int>
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            intent.putExtra("FoodItemName", foodName as ArrayList<String>)
            intent.putExtra("FoodItemPrice", foodPrice as ArrayList<String>)
            intent.putExtra("FoodItemImage", foodImage as ArrayList<String>)
            intent.putExtra("FoodItemDescripion", foodDescription as ArrayList<String>)
            intent.putExtra("FoodItemQuantity", foodQuantities as ArrayList<Int>)
            startActivity(intent)
        }
    }


    private fun retrieveCartItems() {
        // tham chiếu dữ liệu trên Firebase
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val foodReference: DatabaseReference =
            database.reference.child("ClientUser").child(userId).child("GioHang")
        // liệt kê dữ liệu trong giỏ hàng
        foodNames = mutableListOf()
        foodPrices = mutableListOf()
        foodDescriptions = mutableListOf()
        foodIngredients = mutableListOf()
        foodImagesUri = mutableListOf()
        quatitys = mutableListOf()
        // lấy từ database
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    // lấy dữ liệu  GioHangItem object từ các child note
                    val cartItems = foodSnapshot.getValue(GioHangItem::class.java)


                    //add cartitem vào list
                    cartItems?.foodName?.let { foodNames.add(it) }
                    cartItems?.foodPrice?.let { foodPrices.add(it) }
                    cartItems?.foodDescription?.let { foodDescriptions.add(it) }
                    cartItems?.foodImage?.let { foodImagesUri.add(it) }
                    cartItems?.foodQuantity?.let { quatitys.add(it) }
//                    cartItems?.foodIngredient?.let { foodIngredients.add(it) }
                }
                setAdapter()
            }

            private fun setAdapter() {
                cartAdapter =
                    CartAdapter(
                        requireContext(),
                        foodNames,
                        foodPrices,
                        foodDescriptions,
                        foodImagesUri,
                        quatitys
                    )//foodIngredients
                binding.cartRevylerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.cartRevylerView.adapter = cartAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Không lấy dữ liệu thanh công", Toast.LENGTH_SHORT).show()
            }

        })
    }

    companion object {

    }
}