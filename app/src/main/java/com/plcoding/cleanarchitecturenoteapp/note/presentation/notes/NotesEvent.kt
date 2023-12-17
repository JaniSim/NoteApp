package com.plcoding.cleanarchitecturenoteapp.note.presentation.notes

import com.plcoding.cleanarchitecturenoteapp.note.domain.model.Note

sealed class NotesEvent{
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
}
