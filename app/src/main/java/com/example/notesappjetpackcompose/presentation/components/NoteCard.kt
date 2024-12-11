package com.example.notesappjetpackcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesappjetpackcompose.data.Note
import com.example.notesappjetpackcompose.presentation.viewmodel.NoteEvents
import com.example.notesappjetpackcompose.utils.NoteUtils.calculateDelay
import com.example.notesappjetpackcompose.utils.NoteUtils.getTimeAgo
import kotlinx.coroutines.delay

@Composable
fun NoteCard(
    note: Note,
    onEvent: (NoteEvents) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = note.title,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Text(
                text = note.description,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(0.25f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var timeStamp by remember {
                mutableStateOf(getTimeAgo(note.dateAdded, System.currentTimeMillis()))
            }

            LaunchedEffect(note.dateAdded) {
                while(true){
                    timeStamp = getTimeAgo(
                        note.dateAdded,
                        System.currentTimeMillis()
                    )
                    val delay = calculateDelay(
                        note.dateAdded,
                        System.currentTimeMillis()
                    )
                    delay(delay)
                }
            }

            IconButton(
                onClick = {
                    onEvent(NoteEvents.DeleteNote(note))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Note",
                    modifier = Modifier.size(50.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                fontSize = 12.sp,
                text = timeStamp,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}




