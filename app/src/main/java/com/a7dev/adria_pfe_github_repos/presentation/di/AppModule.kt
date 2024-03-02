package com.a7dev.adria_pfe_github_repos.presentation.di

import com.a7dev.adria_pfe_github_repos.data.dao.GithubRepositoryDao
import com.a7dev.adria_pfe_github_repos.data.remote.ApiService
import com.a7dev.adria_pfe_github_repos.data.repository.GithubRepositoriesImpl
import com.a7dev.adria_pfe_github_repos.domain.repository.GithubRepositories
import com.a7dev.adria_pfe_github_repos.domain.usecase.GetRepos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRepo(apiService: ApiService, dao: GithubRepositoryDao) : GithubRepositories{
        return GithubRepositoriesImpl(apiService, dao)
    }

    @Provides
    fun provideGetReposUseCase(repository: GithubRepositories) : GetRepos{
        return GetRepos(repository)
    }
}