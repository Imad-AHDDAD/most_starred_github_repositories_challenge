package com.a7dev.adria_pfe_github_repos.presentation.ui.languagesettings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a7dev.adria_pfe_github_repos.R
import com.a7dev.adria_pfe_github_repos.databinding.FragmentLangageSettingsBinding

class LanguageSettingsFragment : Fragment() {
    private lateinit var binding: FragmentLangageSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLangageSettingsBinding.inflate(layoutInflater)
        binding.apply {
            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            englishTV.setOnClickListener {
                changeLanguage("en")
            }
            frenchTV.setOnClickListener {
                changeLanguage("fr")
            }
        }
        loadPreferences()
        return binding.root
    }

    private fun goTo(action: Int) {
        findNavController().navigate(action);
    }

    private fun changeLanguage(languageCode: String?) {
        if (languageCode.equals("fr")) {
            binding.radioFrench.isChecked = true
            binding.radioEnglish.isChecked = false
        } else {
            binding.radioFrench.isChecked = false
            binding.radioEnglish.isChecked = true
        }
        saveLanguage((languageCode!!))
        if (activity is LanguageChangeListener) {
            (activity as LanguageChangeListener).onLanguageChanged(languageCode)
        }
    }

    private fun saveLanguage(language: String) {
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("language", language)
        editor?.apply()
    }

    private fun loadPreferences() {
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val language = sharedPreferences?.getString("language", "en")
        if (language.equals("fr")) {
            binding.radioFrench.isChecked = true
            binding.radioEnglish.isChecked = false
        } else {
            binding.radioFrench.isChecked = false
            binding.radioEnglish.isChecked = true
        }
    }

    interface LanguageChangeListener {
        fun onLanguageChanged(code: String)
    }
}