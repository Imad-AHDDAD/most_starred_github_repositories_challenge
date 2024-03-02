package com.a7dev.adria_pfe_github_repos.presentation.ui.displaymodesettings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.a7dev.adria_pfe_github_repos.R
import com.a7dev.adria_pfe_github_repos.databinding.FragmentDisplayModeBinding

class DisplayModeFragment : Fragment() {
    private lateinit var binding: FragmentDisplayModeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplayModeBinding.inflate(layoutInflater)
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            lightTV.setOnClickListener {
                setDisplayMode("light")
            }
            darkTV.setOnClickListener {
                setDisplayMode("dark")
            }
        }
        loadPreferences()
        return binding.root
    }
    private fun loadPreferences(){
        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val theme = sharedPreferences?.getString("mode", "light")
        setDisplayMode(theme!!)
    }
    private fun saveMode(mode: String){
        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("mode", mode)
        editor?.apply()
    }
    private fun setDisplayMode(mode: String) {
        saveMode(mode)
        if(mode == "dark") {
            binding.radioDark.isChecked = true
            binding.radioLight.isChecked = false
        }else {
            binding.radioDark.isChecked = false
            binding.radioLight.isChecked = true
        }
        if (activity is DisplayModeChangeListener) {
            (activity as DisplayModeChangeListener).onDisplayModeChanged(mode)
        }
    }
    private fun goTo(action: Int) {
        findNavController().navigate(action);
    }
    interface DisplayModeChangeListener {
        fun onDisplayModeChanged(mode: String)
    }
}