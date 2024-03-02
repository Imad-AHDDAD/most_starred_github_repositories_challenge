package com.a7dev.adria_pfe_github_repos.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.a7dev.adria_pfe_github_repos.data.dto.GithubRepository

@Dao
interface GithubRepositoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(githubRepository: GithubRepository): Long

    @Query("SELECT COUNT(*) FROM github_repos_table WHERE name = :name AND owner_login = :ownerLogin AND avatar_url = :avatarUrl AND description = :description AND stargazers_count = :stargazersCount AND html_url = :htmlUrl")
    suspend fun checkExistence(
        name: String,
        ownerLogin: String,
        avatarUrl: String,
        description: String,
        stargazersCount: Int,
        htmlUrl: String
    ): Int

    @Query("SELECT * FROM github_repos_table")
    fun getFavoriteRepos(): LiveData<List<GithubRepository>>

    @Delete
    suspend fun deleteFavorite(githubRepository: GithubRepository): Int
}