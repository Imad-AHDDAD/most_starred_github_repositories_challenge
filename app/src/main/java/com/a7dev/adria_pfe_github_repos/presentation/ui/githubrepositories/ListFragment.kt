package com.a7dev.adria_pfe_github_repos.presentation.ui.githubrepositories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7dev.adria_pfe_github_repos.R
import com.a7dev.adria_pfe_github_repos.data.dto.GithubRepository
import com.a7dev.adria_pfe_github_repos.databinding.FragmentListBinding
import com.a7dev.adria_pfe_github_repos.domain.entity.Items
import com.a7dev.adria_pfe_github_repos.presentation.common.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListFragment : Fragment(), ReposAdapter.RepositoriesListeners {
    private val viewModel: ListViewModel by activityViewModels()
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: ReposAdapter
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater)
        binding.apply {
            menuBtn.setOnClickListener {
                showDrawer()
            }
            settingsBtn.setOnClickListener {
                goToSettings()
            }
        }
        hideListAndShowLoading()
        setupRecyclerView()
        getData(1)
        return binding.root
    }

    private fun goToSettings() {
        findNavController().navigate(R.id.action_listFragment_to_settingsFragment);
    }

    private fun setupRecyclerView() {
        adapter = ReposAdapter(requireContext(), this)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.listRV.layoutManager = layoutManager
        binding.listRV.adapter = adapter
    }

    private fun getData(page: Int) {
        hideListAndShowLoading()
        viewModel.getMostStarredRepositories(page)
        lifecycleScope.launch {
            viewModel.mostStarredRepos.collect {
                it?.items?.let { it1 ->
                    adapter.refillList(it1)
                    adapter.setPage(page)
                    binding.listRV.scrollToPosition(0)
                    showListAndHideLoading()
                }
            }
        }
    }

    private fun showListAndHideLoading() {
        binding.loadingCC.visibility = View.GONE
        binding.listRV.visibility = View.VISIBLE
    }
    private fun hideListAndShowLoading() {
        binding.loadingCC.visibility = View.VISIBLE
        binding.listRV.visibility = View.GONE
    }
    private fun showDrawer() {
        drawerLayout = activity?.findViewById(R.id.drawerLayout)!!
        drawerLayout.openDrawer(GravityCompat.START)
    }
    override fun onNextPageClicked(next: Int) {
        getData(next)
    }
    override fun onFavoriteClicked(items: Items) {
        val repoToAdd = GithubRepository(
            id = null,
            name = items.name,
            owner_login = items.owner.login,
            avatar_url = items.owner.avatar_url,
            description = items.description,
            stargazers_count = items.stargazers_count,
            html_url = items.html_url
        )
        lifecycleScope.launch {
            viewModel.check(repoToAdd).collectLatest { count ->
                if (count == 0) {
                    viewModel.addRepoToFavorite(repoToAdd).collectLatest { id ->
                        if (id != -1L) {
                            showToast("added successfully")
                        } else {
                            showToast("Error adding element")
                        }
                    }
                } else {
                    showToast("Element is already in favorite list")
                }
            }
        }

    }

}