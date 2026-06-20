package com.universalwatermark.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universalwatermark.data.local.db.dao.WatermarkHistoryDao
import com.universalwatermark.data.local.db.entity.WatermarkHistoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WatermarkHistoryViewModel @Inject constructor(
    historyDao: WatermarkHistoryDao
) : ViewModel() {

    val historyList: StateFlow<List<WatermarkHistoryEntity>> = historyDao.getAllHistory()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
