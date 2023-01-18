package com.example.mynoteapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var noteTitleEdt: EditText
    lateinit var noteDescriptionEdt: EditText
    lateinit var addupdateBtn: Button
    lateinit var viewModel: NoteViewModel
    lateinit var noteTitle: String
    lateinit var noteDesc: String
    lateinit var noteDate: String

    var noteID = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        noteTitleEdt = findViewById(R.id.idEditNoteTitle)
        noteDescriptionEdt = findViewById(R.id.idEditNoteDescription)
        addupdateBtn = findViewById(R.id.idBtnAddUpdate)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            noteTitle = intent.getStringExtra("noteTitle")!!
            noteDesc = intent.getStringExtra("noteDescription")!!
            noteDate = intent.getStringExtra("noteDate")!!

            noteTitleEdt.setText(noteTitle)
            noteDescriptionEdt.setText(noteDesc)
            addupdateBtn.setText("Update Note")
        } else {
            addupdateBtn.setText("Save Note")
        }
        addupdateBtn.setOnClickListener {

            val noteTitle = noteTitleEdt.text.toString()
            val noteDesc = noteDescriptionEdt.text.toString()
            //Toast.makeText(applicationContext, noteTitle+" "+noteDesc, Toast.LENGTH_LONG).show()
            //Toast.makeText(applicationContext, noteType, Toast.LENGTH_LONG).show()

            if (noteType.equals("Edit")) {
                Log.i("debugg", "inside notetype if")
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()) {
                    Log.i("debugg", "inside null check if")
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updateNote = Note(noteTitle, noteDesc, currentDate)
                    Log.i("debugg", noteTitle+" "+noteDesc)
                    viewModel.addNote(updateNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Note title or desc cannot be empty!!", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Log.i("debugg", "inside else")
                if (noteTitle.isNotEmpty() && noteDesc.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    viewModel.addNote(Note(noteTitle, noteDesc, currentDate))
                    Toast.makeText(this, "Note Added..", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }

    override fun onBackPressed() {
        viewModel.addNote(Note(noteTitle, noteDesc, noteDate))

        startActivity(Intent(applicationContext, MainActivity::class.java))
        this.finish()
    }
}