package com.universalwatermark.ui.screen.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universalwatermark.data.local.db.dao.WatermarkTemplateDao
import com.universalwatermark.data.local.db.entity.WatermarkTemplateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplateEditorViewModel @Inject constructor(
    private val templateDao: WatermarkTemplateDao
) : ViewModel() {

    private val _templateText = MutableStateFlow("[DATE] [TIME]\n[GPS]\n[USER]")
    val templateText: StateFlow<String> = _templateText.asStateFlow()

    private var activeTemplateId: Long? = null

    init {
        loadActiveTemplate()
    }

    private fun loadActiveTemplate() {
        viewModelScope.launch {
            val template = templateDao.getActiveTemplateSync()
            if (template != null) {
                activeTemplateId = template.id
                _templateText.value = template.templateText
            }
        }
    }

    fun updateTemplateText(newText: String) {
        _templateText.value = newText
    }

    fun appendPlaceholder(placeholder: String) {
        _templateText.value = _templateText.value + " " + placeholder
    }

    fun saveTemplate() {
        viewModelScope.launch {
            val entity = WatermarkTemplateEntity(
                id = activeTemplateId ?: 0L,
                name = "Default Template",
                templateText = _templateText.value,
                isActive = true,
                styleJson = "{}", // Mocked
                positionJson = "{}", // Mocked
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            
            if (activeTemplateId == null) {
                templateDao.deactivateAllTemplates()
                templateDao.insertTemplate(entity)
            } else {
                templateDao.updateTemplate(entity)
            }
        }
    }
}
