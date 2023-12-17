package com.plcoding.cleanarchitecturenoteapp.note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note : Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("Empty title")
        }
        if(note.content.isBlank()){
            throw InvalidNoteException("Empty content")
        }
        repository.insertNote(note)
    }

}