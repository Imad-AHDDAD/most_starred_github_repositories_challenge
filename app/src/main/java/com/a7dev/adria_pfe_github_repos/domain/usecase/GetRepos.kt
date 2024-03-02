package com.a7dev.adria_pfe_github_repos.domain.usecase
import com.a7dev.adria_pfe_github_repos.domain.repository.GithubRepositories
import javax.inject.Inject

class GetRepos @Inject constructor(
    private val githubRepositories: GithubRepositories
) {
    suspend operator fun invoke(q: String?, sort: String?, order: String?, page: Int?) =
        githubRepositories.getMostStarredGithubRepositories(q, sort, order, page)
}