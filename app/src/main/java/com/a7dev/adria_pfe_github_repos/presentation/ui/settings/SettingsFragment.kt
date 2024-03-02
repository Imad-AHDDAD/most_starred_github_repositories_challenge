package com.a7dev.adria_pfe_github_repos.presentation.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.a7dev.adria_pfe_github_repos.R
import com.a7dev.adria_pfe_github_repos.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        binding.apply {
            homeBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            menuBtn.setOnClickListener {
                showDrawer()
            }
            langageSetingsCV.setOnClickListener {
                goTo(R.id.action_settingsFragment_to_languageSettingsFragment)
            }
            modeSetingsCV.setOnClickListener {
                goTo(R.id.action_settingsFragment_to_displayModeFragment)
            }
        }
        return binding.root
    }

    private fun goTo(action: Int) {
        findNavController().navigate(action);
    }
    private fun showDrawer() {
        drawerLayout = activity?.findViewById(R.id.drawerLayout)!!
        drawerLayout.openDrawer(GravityCompat.START)
    }

}