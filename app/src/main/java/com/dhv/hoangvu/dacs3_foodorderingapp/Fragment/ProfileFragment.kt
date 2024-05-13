package com.dhv.hoangvu.dacs3_foodorderingapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.FragmentProfileBinding
import com.dhv.hoangvu.dacs3_foodorderingapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        setUserData()

        binding.saveInfoButton.setOnClickListener {
            val name = binding.name.text.toString()
            val address = binding.address.text.toString()
            val phone = binding.phone.text.toString()
            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Vui lòng nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                updateUserData(name, address, phone)
            }
        }
        return binding.root
    }

    private fun updateUserData(name: String, address: String, phone: String) {
        val usreIn = auth.currentUser?.uid
        if (usreIn != null) {
            val userReference = database.getReference("ClientUser").child(usreIn)
            val userData = hashMapOf(
                "name" to name,
                "address" to address,
                "phone" to phone,
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Cập nhật hồ sơ thành công", Toast.LENGTH_SHORT)
                    .show()
            }
                .addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Cập nhật hồ sơ không thành công",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
        }
    }

    private fun setUserData() {
        val userId: String? = auth.currentUser?.uid
        if (userId != null) {
            val userReference = database.getReference("ClientUser").child(userId)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val userProfile: UserModel? = snapshot.getValue(UserModel::class.java)
                        if (userProfile != null) {
                            binding.name.setText(userProfile.name)
                            binding.address.setText(userProfile.address)
                            binding.phone.setText(userProfile.phone)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

    }
}