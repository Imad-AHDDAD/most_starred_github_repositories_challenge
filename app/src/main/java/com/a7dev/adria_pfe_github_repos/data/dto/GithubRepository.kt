package com.a7dev.adria_pfe_github_repos.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_repos_table")
data class GithubRepository(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "owner_login") val owner_login: String?,
    @ColumnInfo(name = "avatar_url") val avatar_url: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "stargazers_count") val stargazers_count: Int?,
    @ColumnInfo(name = "html_url") val html_url: String?
)