package com.plcoding.cleanarchitecturenoteapp.note.presentation.add_edit_note

data class NoteTextFieldState(
    var text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
