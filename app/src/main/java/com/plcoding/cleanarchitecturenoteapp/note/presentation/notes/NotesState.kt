package com.plcoding.cleanarchitecturenoteapp.note.presentation.notes

import com.plcoding.cleanarchitecturenoteapp.note.domain.model.Note

data class NotesState(
    val notes : List<Note> = emptyList()
)
