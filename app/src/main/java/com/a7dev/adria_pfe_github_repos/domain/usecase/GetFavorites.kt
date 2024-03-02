package com.a7dev.adria_pfe_github_repos.domain.usecase

import androidx.lifecycle.LiveData
import com.a7dev.adria_pfe_github_repos.data.dto.GithubRepository
import com.a7dev.adria_pfe_github_repos.domain.repository.GithubRepositories
import javax.inject.Inject

class GetFavorites @Inject constructor(
    private val githubRepositories: GithubRepositories
) {
    suspend operator fun invoke(): LiveData<List<GithubRepository>> = githubRepositories.getFavoriteReposFromRoom()
}