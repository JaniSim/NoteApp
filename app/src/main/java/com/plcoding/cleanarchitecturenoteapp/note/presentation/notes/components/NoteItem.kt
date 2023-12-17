package com.plcoding.cleanarchitecturenoteapp.note.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.note.domain.model.Note

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    onDeleteClick:  () -> Unit
) {
    Box(
        modifier = modifier
    ){
        Canvas(modifier = Modifier.matchParentSize()){
            drawRoundRect(
                color = Color(note.color),
                size = size
            )
        }
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(end = 32.dp)
        ) {
            Text( text = note.title
            , style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
            , maxLines = 1,
                overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(8.dp))
            Text( text = note.title
                , style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
                , maxLines = 10,
                overflow = TextOverflow.Ellipsis)
        }
        IconButton(onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete note"
            )
        }
    }
    

}