package com.a7dev.adria_pfe_github_repos.data.repository

import androidx.lifecycle.LiveData
import com.a7dev.adria_pfe_github_repos.data.dao.GithubRepositoryDao
import com.a7dev.adria_pfe_github_repos.data.dto.GithubRepository
import com.a7dev.adria_pfe_github_repos.data.remote.ApiService
import com.a7dev.adria_pfe_github_repos.domain.entity.Model
import com.a7dev.adria_pfe_github_repos.domain.repository.GithubRepositories
import javax.inject.Inject

class GithubRepositoriesImpl @Inject constructor(
    private val apiService: ApiService, private val dao: GithubRepositoryDao
) : GithubRepositories {
    override suspend fun getMostStarredGithubRepositories(
        q: String?,
        sort: String?,
        order: String?,
        page: Int?
    ): Model {
        return apiService.getMostStarredGithubRepositories(q, sort, order, page)
    }
    override suspend fun insertToRoom(githubRepository: GithubRepository): Long {
        return dao.insert(githubRepository)
    }
    override suspend fun getFavoriteReposFromRoom(): LiveData<List<GithubRepository>> {
        return dao.getFavoriteRepos()
    }
    override suspend fun deleteFavoriteFromRoom(githubRepository: GithubRepository): Int {
        return dao.deleteFavorite(githubRepository)
    }
    override suspend fun checkIfExists(g: GithubRepository): Int {
        return dao.checkExistence(g.name!!, g.owner_login!!, g.avatar_url!!, g.description!!, g.stargazers_count!!, g.html_url!!)
    }
}