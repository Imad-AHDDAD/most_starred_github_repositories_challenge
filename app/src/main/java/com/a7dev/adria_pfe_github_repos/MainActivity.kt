package com.a7dev.adria_pfe_github_repos

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.a7dev.adria_pfe_github_repos.databinding.ActivityMainBinding
import com.a7dev.adria_pfe_github_repos.presentation.ui.displaymodesettings.DisplayModeFragment
import com.a7dev.adria_pfe_github_repos.presentation.ui.languagesettings.LanguageSettingsFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    DisplayModeFragment.DisplayModeChangeListener,
    LanguageSettingsFragment.LanguageChangeListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        loadPreferences()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSideBarNav()
    }
    private fun loadPreferences() {
        val sharedPreferences: SharedPreferences? =
            getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val language = sharedPreferences?.getString("language", "en")
        val theme = sharedPreferences?.getString("mode", "light")
        if (language.equals("fr")) setLocale(this, language)
        applyDisplayMode(theme)
    }
    private fun applyDisplayMode(theme: String?) {
        when (theme) {
            "light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            "dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
    private fun setLocale(activity: Context, languageCode: String?) {
        val locale = languageCode?.let { Locale(it) }
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
    private fun restart() {
        this.recreate()
    }
    private fun setupSideBarNav() {
        navController = findNavController(R.id.fragmentContainerView)
        drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navigationView
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.sidebar_trending -> goTo(R.id.listFragment)
                R.id.sidebar_settings -> goTo(R.id.settingsFragment)
                R.id.sidebar_favorite -> goTo(R.id.favoriteListFragment)
            }
            true
        }
    }
    private fun goTo(action: Int) {
        navController.navigate(action)
        drawerLayout.closeDrawer(GravityCompat.START)
    }
    override fun onDisplayModeChanged(mode: String) {
        when (mode) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
    override fun onLanguageChanged(code: String) {
        setLocale(this@MainActivity, code)
        restart()
    }
}