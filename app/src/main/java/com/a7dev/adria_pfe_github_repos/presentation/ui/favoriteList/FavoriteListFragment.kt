package com.a7dev.adria_pfe_github_repos.presentation.ui.favoriteList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7dev.adria_pfe_github_repos.R
import com.a7dev.adria_pfe_github_repos.data.dto.GithubRepository
import com.a7dev.adria_pfe_github_repos.databinding.FragmentFavoriteListBinding
import com.a7dev.adria_pfe_github_repos.domain.entity.Items
import com.a7dev.adria_pfe_github_repos.presentation.common.showToast
import com.a7dev.adria_pfe_github_repos.presentation.ui.githubrepositories.ReposAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteListFragment : Fragment(), FavoriteListAdapter.FavoriteRepositoriesListeners {
    private val viewModel: FavoriteListViewModel by activityViewModels()
    private lateinit var binding : FragmentFavoriteListBinding
    private lateinit var adapter: FavoriteListAdapter
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteListBinding.inflate(layoutInflater)
        binding.apply {
            homeBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            menuBtn.setOnClickListener {
                showDrawer()
            }
        }
        hideListAndShowLoading()
        setupRecyclerView()
        getData()
        return binding.root
    }
    private fun setupRecyclerView() {
        adapter = FavoriteListAdapter(requireContext(), this)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.listRV.layoutManager = layoutManager
        binding.listRV.adapter = adapter
    }
    private fun goTo(action: Int) {
        findNavController().navigate(action);
    }
    private fun getData() {
        hideListAndShowLoading()
        viewModel.getFavoriteList()
        lifecycleScope.launch {
            viewModel.list.observe(viewLifecycleOwner) {list ->
                adapter.refillList(list)
                showListAndHideLoading()
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
    override fun onDeleteClicked(item: GithubRepository) {
        lifecycleScope.launch {
            viewModel.delete(item).collectLatest { count ->
                if (count != null) {
                    if(count > 0) {
                        showToast("Deleted successfully")
                    }else {
                        showToast("Error")
                    }
                }else {
                    showToast("Error null")
                }
            }
        }
    }

}