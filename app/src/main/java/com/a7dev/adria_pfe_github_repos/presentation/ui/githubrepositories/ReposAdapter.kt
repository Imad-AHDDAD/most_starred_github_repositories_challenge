package com.a7dev.adria_pfe_github_repos.presentation.ui.githubrepositories

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a7dev.adria_pfe_github_repos.R
import com.a7dev.adria_pfe_github_repos.data.dto.GithubRepository
import com.a7dev.adria_pfe_github_repos.databinding.ItemBinding
import com.a7dev.adria_pfe_github_repos.domain.entity.Items
import com.bumptech.glide.Glide

class ReposAdapter(
    private val context: Context,
    private val repositoriesListeners: RepositoriesListeners
) :
    RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {

    private var reposList = ArrayList<Items>()
    private var page: Int = 1
    fun setPage(newPage: Int) {
        page = newPage
    }

    fun refillList(list: ArrayList<Items>) {
        reposList.clear()
        reposList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ReposViewHolder(view)
    }

    override fun getItemCount(): Int = reposList.size
    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.binding.favoriteBtn.visibility = View.VISIBLE
        holder.binding.deleteBtn.visibility = View.GONE
        val item = reposList[position]
        holder.binding.apply {
            idRepositoryNameTV.text = item.name
            idRepositoryDescriptionTV.text = item.description
            idStarsNumberTV.text = getStarsZoneText(item.stargazers_count)
            idOwnerNameTV.text = item.owner.login
            Glide.with(context).load(item.owner.avatar_url)
                .placeholder(R.drawable.avatar)
                .into(holder.binding.idProfileImageIV)

            holder.binding.card.setOnClickListener {
                openRepoInBrowser(item.html_url)
            }
            favoriteBtn.setOnClickListener {
                repositoriesListeners.onFavoriteClicked(item)
            }
        }
        if (reposList.last() == item) {
            holder.binding.apply {
                paginationCC.visibility = View.VISIBLE
                pageTV.text = "Page $page"
                if (page > 1) {
                    previousPageBtn.visibility = View.VISIBLE
                } else {
                    previousPageBtn.visibility = View.INVISIBLE
                }
                nextPageBtn.setOnClickListener {
                    repositoriesListeners.onNextPageClicked(page + 1)
                }
                previousPageBtn.setOnClickListener {
                    if (page - 1 >= 1) {
                        repositoriesListeners.onNextPageClicked(page - 1)
                    }
                }
            }
        } else {
            holder.binding.apply {
                paginationCC.visibility = View.GONE
            }
        }

    }

    private fun getStarsZoneText(starsNumber: Int): String {
        val text = if (starsNumber >= 1000) {
            val result = starsNumber.toFloat() / 1000
            val formattedResult = String.format("%.1f", result)
            "$formattedResult K"
        } else {
            "$starsNumber"
        }
        return text
    }

    private fun openRepoInBrowser(htmlUrl: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(htmlUrl)
        context.startActivity(openURL)
    }

    inner class ReposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemBinding = ItemBinding.bind(itemView)
    }

    interface RepositoriesListeners {
        fun onNextPageClicked(next: Int)
        fun onFavoriteClicked(items: Items)
    }
}