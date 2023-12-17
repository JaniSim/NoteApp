package com.plcoding.cleanarchitecturenoteapp.note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.note.domain.repository.NoteRepository

class GetNote (
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}