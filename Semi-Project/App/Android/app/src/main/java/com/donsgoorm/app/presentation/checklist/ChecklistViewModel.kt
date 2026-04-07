package com.donsgoorm.app.presentation.checklist

import androidx.lifecycle.ViewModel
import com.donsgoorm.app.presentation.checklist.model.ChecklistSection
import com.donsgoorm.app.presentation.checklist.model.placeholderSections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ChecklistViewModel @Inject constructor() : ViewModel() {

    private val _sections = MutableStateFlow(placeholderSections())
    val sections: StateFlow<List<ChecklistSection>> = _sections

    fun toggleTask(sectionId: String, taskId: String) {
        _sections.value = _sections.value.map { section ->
            if (section.id != sectionId) return@map section
            section.copy(tasks = section.tasks.map { task ->
                if (task.id == taskId) task.copy(isCompleted = !task.isCompleted) else task
            })
        }
    }

    fun toggleDocument(sectionId: String, taskId: String, docId: String) {
        _sections.value = _sections.value.map { section ->
            if (section.id != sectionId) return@map section
            section.copy(tasks = section.tasks.map { task ->
                if (task.id != taskId) return@map task
                task.copy(documents = task.documents.map { doc ->
                    if (doc.id == docId) doc.copy(isReady = !doc.isReady) else doc
                })
            })
        }
    }
}
