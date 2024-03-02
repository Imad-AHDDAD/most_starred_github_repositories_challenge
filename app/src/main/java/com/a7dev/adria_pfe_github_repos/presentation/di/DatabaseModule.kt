package com.a7dev.adria_pfe_github_repos.presentation.di

import android.content.Context
import androidx.room.Room
import com.a7dev.adria_pfe_github_repos.data.dao.GithubRepositoryDao
import com.a7dev.adria_pfe_github_repos.data.database.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME = "github_repositories"
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MyDatabase {
        return Room.databaseBuilder(
            appContext,
            MyDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(database: MyDatabase): GithubRepositoryDao {
        return database.getDao()
    }
}