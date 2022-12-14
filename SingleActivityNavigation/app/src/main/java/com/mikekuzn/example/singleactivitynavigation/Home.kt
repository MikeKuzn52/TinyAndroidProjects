package com.mikekuzn.example.singleactivitynavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mikekuzn.example.singleactivitynavigation.databinding.FragmentHomeBinding


class Home : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileButton .setOnClickListener { MAIN.navController.navigate(R.id.action_home_to_profile) }
        binding.SettingsButton.setOnClickListener { MAIN.navController.navigate(R.id.action_home_to_setings) }
        binding.AboutButton   .setOnClickListener { MAIN.navController.navigate(R.id.action_home_to_about  ) }
    }
}