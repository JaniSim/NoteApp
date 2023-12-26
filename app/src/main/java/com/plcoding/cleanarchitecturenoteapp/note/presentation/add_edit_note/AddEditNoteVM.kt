package com.plcoding.cleanarchitecturenoteapp.note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteVM  @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _title = mutableStateOf(
        NoteTextFieldState(
        hint = "Enter title..."
    )
    )
    val title: State<NoteTextFieldState> = _title

    private val _content = mutableStateOf(NoteTextFieldState(
        hint = "Enter some content"
    ))
    val content: State<NoteTextFieldState> = _content

    private val _image = mutableStateOf(NoteTextFieldState(
        hint = "Select an image"
    ))
    val image: State<NoteTextFieldState> = _image

    private val _color = mutableStateOf(Note.noteColors.random().toArgb())
    val color: State<Int> = _color

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _title.value = title.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _content.value = _content.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _color.value = note.color
                        _image.value = image.value.copy(
                            text = note.image,
                            isHintVisible = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _title.value = title.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _title.value = title.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            title.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _content.value = _content.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.EnteredImage -> {
                _image.value = image.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _content.value = _content.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _content.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _color.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = title.value.text,
                                content = content.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = color.value,
                                id = currentNoteId,
                                image = image.value.text
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch(e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}