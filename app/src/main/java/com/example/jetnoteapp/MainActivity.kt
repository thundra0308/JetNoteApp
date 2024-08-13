package com.example.jetnoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetnoteapp.screen.NoteScreen
import com.example.jetnoteapp.screen.NoteScreenViewModel
import com.example.jetnoteapp.ui.theme.JetNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetNoteAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val noteScreenViewModel: NoteScreenViewModel by viewModels()
                    NoteApp(noteScreenViewModel)
                }
            }
        }
    }
}

@Composable
fun NoteApp(noteScreenViewModel: NoteScreenViewModel = viewModel()) {
    val notes = noteScreenViewModel.noteList.collectAsState().value
    NoteScreen(
        notes,
        onAddNote = { noteScreenViewModel.addNote(it) },
        onRemoveNote = { noteScreenViewModel.removeNote(it) }
    )
}

@Preview
@Composable
fun DefaultPreview() {
    NoteScreen()
}

