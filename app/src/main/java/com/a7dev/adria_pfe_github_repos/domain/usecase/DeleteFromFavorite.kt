package com.a7dev.adria_pfe_github_repos.domain.usecase

import com.a7dev.adria_pfe_github_repos.data.dto.GithubRepository
import com.a7dev.adria_pfe_github_repos.domain.repository.GithubRepositories
import javax.inject.Inject

class DeleteFromFavorite @Inject constructor(
    private val githubRepositories: GithubRepositories
) {
    suspend operator fun invoke(githubRepository: GithubRepository): Int =
        githubRepositories.deleteFavoriteFromRoom(githubRepository)

}