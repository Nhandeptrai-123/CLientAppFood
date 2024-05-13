package com.dhv.hoangvu.dacs3_foodorderingapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.ActivityPayOutBinding
import com.dhv.hoangvu.dacs3_foodorderingapp.model.OrderDetai
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class PayOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayOutBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var totalAmount: String
    private lateinit var foodItemName: ArrayList<String>
    private lateinit var foodItemPrice: ArrayList<String>
    private lateinit var foodItemImage: ArrayList<String>
    private lateinit var foodItemDescription: ArrayList<String>
    private lateinit var foodItemQuantities: ArrayList<Int>
    private lateinit var databaseRefence: DatabaseReference
    private lateinit var userID: String


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        //Khởi tạo firebase và chi tiết UserClient
        auth = FirebaseAuth.getInstance()
        databaseRefence = FirebaseDatabase.getInstance().reference

        // Lưu dữ liệu User có liên quan đến Profileactivity.kt
        //setUserData()

        //lấy chi tiết dữ liệu người dùng ừ Firebase
        val intent = intent
        foodItemName = intent.getStringArrayListExtra("FoodItemName") as ArrayList<String>
        foodItemPrice = intent.getStringArrayListExtra("FoodItemPrice") as ArrayList<String>
        foodItemImage = intent.getStringArrayListExtra("FoodItemImage") as ArrayList<String>
        foodItemDescription =
            intent.getStringArrayListExtra("FoodItemDescripion") as ArrayList<String>
        foodItemQuantities = intent.getIntegerArrayListExtra("FoodItemQuantity") as ArrayList<Int>


        totalAmount = calculateTotalAmount().toString() + "đ"
        binding.totalAmount.setText(totalAmount)





        binding.btnThanhToan.setOnClickListener {
            //lấy data từ form cụ thể textview đưa lên fire base lưu vào nhánh con tên
            name = binding.name.text.toString().trim()
            address = binding.addres.text.toString().trim()
            phone = binding.phone.text.toString().trim()

            if (name.isBlank() && address.isBlank() && phone.isBlank()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                plaseOrder()
            }


        }


        binding.btnBack.setOnClickListener {
            finish()
        }

    }


    private fun plaseOrder() {
        userID = auth.currentUser?.uid ?: ""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseRefence.child("DatHang").push().key
        val orderDetails = OrderDetai(
            userID,
            name,
            foodItemName,
            foodItemPrice,
            foodItemImage,
            foodItemQuantities,
            address,
            totalAmount,
            phone,
            time,
            itemPushKey,
            false,
            false
        )
        val orderRefernce = databaseRefence.child("DatHang").child(itemPushKey!!)
        orderRefernce.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDiaglog = CongratsBottomSheet()
            bottomSheetDiaglog.show(supportFragmentManager, "CongratsBottomSheet")
            removeItemFromCart()
            addOrderHistory(orderDetails)
        }
            .addOnFailureListener {
                Toast.makeText(this, "Lỗi đặt hàng", Toast.LENGTH_SHORT).show()
            }

    }

    //hàm này lưu dữ liệu hoá đơn đã thanh toán
    private fun addOrderHistory(orderDetails: OrderDetai) {
        databaseRefence.child("ClientUser").child(userID).child("HoaDon")
            .child(orderDetails.itemPushKey!!)
            .setValue(orderDetails).addOnSuccessListener {

            }

    }

    //hàm này xoá các dữ liệu có trong giỏ hàng sau khi khách hàng thanh toán
    private fun removeItemFromCart() {
        val cartitemReference = databaseRefence.child("ClientUser").child(userID).child("GioHang")
        cartitemReference.removeValue()
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (i in 0 until foodItemPrice.size) {
            var price = foodItemPrice[i]
            val lastchar = price.last()
            val priceIntValue = if (lastchar == 'đ') {
                price.dropLast(1).toInt()
            } else {
                price.toInt()
            }
            var quatity = foodItemQuantities[i]
            totalAmount += priceIntValue * quatity
        }
        return totalAmount
    }


//     sau khi thêm chức năng Lưu Profile thì chức năng setUserData này có tác dụng
//    private fun setUserData() {
//        val user = auth.currentUser
//        if(user != null){
//            val userId = user.uid
//            val userReference = databaseRefence.child("ClientUser").child(userId)
//
//            userReference.addListenerForSingleValueEvent(object: ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    if(snapshot.exists()){
//                        val names = snapshot.child("name").getValue(String::class.java)?:""
//                        val addresses = snapshot.child("address").getValue(String::class.java)?:""
//                        val phones = snapshot.child("phone").getValue(String::class.java)?:""
//                        binding.apply {
//                            name.setText(names)
//                            addres.setText(addresses)
//                            phone.setText(phones)
//
//
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//
//
//            })
//        }
//    }
}