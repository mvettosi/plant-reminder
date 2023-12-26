/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.mvettosi.plantreminder.feature.plant.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import io.github.mvettosi.plantreminder.core.data.PlantRepository
import io.github.mvettosi.plantreminder.feature.plant.ui.PlantUiState.Error
import io.github.mvettosi.plantreminder.feature.plant.ui.PlantUiState.Loading
import io.github.mvettosi.plantreminder.feature.plant.ui.PlantUiState.Success
import javax.inject.Inject

@HiltViewModel
class PlantViewModel @Inject constructor(
    private val plantRepository: PlantRepository
) : ViewModel() {

    val uiState: StateFlow<PlantUiState> = plantRepository
        .plants.map<List<String>, PlantUiState> { Success(data = it) }
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addPlant(name: String) {
        viewModelScope.launch {
            plantRepository.add(name)
        }
    }
}

sealed interface PlantUiState {
    object Loading : PlantUiState
    data class Error(val throwable: Throwable) : PlantUiState
    data class Success(val data: List<String>) : PlantUiState
}
