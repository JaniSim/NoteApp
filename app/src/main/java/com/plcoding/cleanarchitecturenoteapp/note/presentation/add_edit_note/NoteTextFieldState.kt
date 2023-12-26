package com.plcoding.cleanarchitecturenoteapp.note.presentation.add_edit_note

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val image: String = "",
    val isHintVisible: Boolean = true
)
