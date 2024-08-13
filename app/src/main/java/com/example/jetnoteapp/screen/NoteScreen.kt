package com.example.jetnoteapp.screen

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetnoteapp.R
import com.example.jetnoteapp.components.NoteButton
import com.example.jetnoteapp.components.NoteForm
import com.example.jetnoteapp.model.Note
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    notes: List<Note> = listOf(), onAddNote: (Note) -> Unit = {}, onRemoveNote: (Note) -> Unit = {}
) {
    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(6.dp)
            .padding(
                top = WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding()
            )
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name), style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                )
            },
            actions = {
                Icon(
                    imageVector = Icons.Rounded.Create,
                    contentDescription = "Icon",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondary)
        )
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteForm(modifier = Modifier.padding(top = 9.dp, bottom = 8.dp),
                text = title.value,
                label = "Title",
                onTextChange = {
                    title.value = it
                })
            NoteForm(modifier = Modifier.padding(top = 9.dp, bottom = 8.dp),
                text = description.value,
                label = "Add a Note",
                onTextChange = {
                    description.value = it
                })
            NoteButton(text = "Save", onClick = {
                if (title.value.isNotEmpty()) {
                    onAddNote(Note(title = title.value, description = description.value))
                    title.value = ""
                    description.value = ""
                    Toast.makeText(context, "Note Saved", Toast.LENGTH_SHORT).show()
                }
            })
        }
        HorizontalDivider(modifier = Modifier.padding(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(notes) { note ->
                NoteRow(note = note) {

                }
            }
        }
    }
}

@Preview
@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note = Note(title = "abcd", description = "abcd"),
    onNoteClicked: (Note) -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Box(modifier = modifier
            .fillMaxSize()
            .border(1.5.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(15.dp))
            .clickable {
                onNoteClicked(note)
            }) {
            Column(
                modifier = modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = note.title,
                    style = TextStyle(fontSize = 20.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                HorizontalDivider(
                    modifier = modifier.padding(10.dp), color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = note.description,
                    style = TextStyle(fontSize = 15.sp),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = modifier.weight(1f))
                HorizontalDivider(
                    modifier = Modifier.padding(10.dp), color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "Last Updated : ${
                        SimpleDateFormat.getDateInstance(3).format(note.updatedOn)
                    }", style = TextStyle(
                        fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.Gray
                    ), maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NoteScreen()
}