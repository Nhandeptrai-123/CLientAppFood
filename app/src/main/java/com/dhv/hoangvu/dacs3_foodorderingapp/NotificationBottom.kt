package com.dhv.hoangvu.dacs3_foodorderingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhv.hoangvu.dacs3_foodorderingapp.adapter.NotificationAdapter
import com.dhv.hoangvu.dacs3_foodorderingapp.databinding.FragmentNotificationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class NotificationBottom : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotificationBottomBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNotificationBottomBinding.inflate(layoutInflater, container, false)
        val notifications = listOf("Notification 1", "Notification 2", "Notification 3")
        val notificationImages = listOf(R.drawable.sad, R.drawable.deliver, R.drawable.success)

        val adapter =
            NotificationAdapter(ArrayList(notifications), ArrayList(notificationImages))

        binding.rvNotification.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotification.adapter = adapter
        return binding.root

    }

    companion object {

    }
}