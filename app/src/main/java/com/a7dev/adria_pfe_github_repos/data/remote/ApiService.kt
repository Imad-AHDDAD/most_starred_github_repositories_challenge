package com.a7dev.adria_pfe_github_repos.data.remote
import com.a7dev.adria_pfe_github_repos.domain.entity.Model
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("repositories")
    suspend fun getMostStarredGithubRepositories(
        @Query("q") q: String ?,
        @Query("sort") sort: String?,
        @Query("order") order: String?,
        @Query("page") page: Int?
    ) : Model
}