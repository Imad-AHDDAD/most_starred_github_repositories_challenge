package com.a7dev.adria_pfe_github_repos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.a7dev.adria_pfe_github_repos.data.dao.GithubRepositoryDao
import com.a7dev.adria_pfe_github_repos.data.dto.GithubRepository

@Database(entities = [GithubRepository::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getDao() : GithubRepositoryDao
}