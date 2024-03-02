package com.a7dev.adria_pfe_github_repos.presentation.ui.githubrepositories

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a7dev.adria_pfe_github_repos.data.dto.GithubRepository
import com.a7dev.adria_pfe_github_repos.domain.entity.Model
import com.a7dev.adria_pfe_github_repos.domain.usecase.CheckExistence
import com.a7dev.adria_pfe_github_repos.domain.usecase.FavoriteRepository
import com.a7dev.adria_pfe_github_repos.domain.usecase.GetRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getRepos: GetRepos,
    private val favoriteRepository: FavoriteRepository,
    private val checkExistence: CheckExistence
) : ViewModel() {

    private val _mostStarredRepos: MutableStateFlow<Model?> = MutableStateFlow(null)
    val mostStarredRepos: StateFlow<Model?> = _mostStarredRepos

    fun getMostStarredRepositories(page: Int, order: String = "desc") {
        val q = getCreatedValue()
        val sort = "stars"
        Log.d("GITHUBREPOS", "in ListViewModel")
        viewModelScope.launch {
            try {
                _mostStarredRepos.value = getRepos(q, sort, order, page)
            } catch (e: Exception) {
                Log.e("ERROR_VM", "error: ${e.message}")
            }
        }
    }
    fun addRepoToFavorite(githubRepository: GithubRepository) : Flow<Long?> = flow {
        emit(
            try {
                favoriteRepository(githubRepository)
            }catch (ex: Exception) {
                null
            }
        )
    }
    fun check(githubRepository: GithubRepository) : Flow<Int?> = flow {
        emit(
            try {
                checkExistence(githubRepository)
            }catch (ex: Exception) {
                null
            }
        )
    }
    private fun getCreatedValue(): String {
        val date30daysAgo: String
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -30)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        date30daysAgo = formatter.format(calendar.time)
        return "created:>$date30daysAgo"
    }

}