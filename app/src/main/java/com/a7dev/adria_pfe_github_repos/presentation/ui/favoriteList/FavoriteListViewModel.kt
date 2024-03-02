package com.a7dev.adria_pfe_github_repos.presentation.ui.favoriteList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a7dev.adria_pfe_github_repos.data.dto.GithubRepository
import com.a7dev.adria_pfe_github_repos.domain.entity.Model
import com.a7dev.adria_pfe_github_repos.domain.usecase.DeleteFromFavorite
import com.a7dev.adria_pfe_github_repos.domain.usecase.GetFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val getFavorites: GetFavorites,
    private val deleteFromFavorite: DeleteFromFavorite
) : ViewModel() {

    lateinit var list : LiveData<List<GithubRepository>>
    fun getFavoriteList() {
        viewModelScope.launch {
            list = getFavorites()
        }
    }
    fun delete(githubRepository: GithubRepository) : Flow<Int?> = flow {
        emit(
            try {
                deleteFromFavorite(githubRepository)
            }catch (ex: Exception) {
                null
            }
        )
    }

}