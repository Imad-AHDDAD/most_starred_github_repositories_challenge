package com.a7dev.adria_pfe_github_repos.domain.repository

import androidx.lifecycle.LiveData
import com.a7dev.adria_pfe_github_repos.data.dto.GithubRepository
import com.a7dev.adria_pfe_github_repos.domain.entity.Model

interface GithubRepositories {
    suspend fun getMostStarredGithubRepositories(
        q: String?,
        sort: String?,
        order: String?,
        page: Int?
    ) : Model

    suspend fun insertToRoom(githubRepository: GithubRepository): Long
    suspend fun getFavoriteReposFromRoom(): LiveData<List<GithubRepository>>
    suspend fun deleteFavoriteFromRoom(githubRepository: GithubRepository): Int
    suspend fun checkIfExists(githubRepository: GithubRepository): Int
}