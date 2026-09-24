package io.example.notes

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class NotesReader : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notes_reader)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var notesTitle :TextView;
        var notesDate : TextView;
        var notesContent: TextView;
        var backButton: MaterialButton;

        notesTitle = findViewById(R.id.notesTitle);
        notesDate = findViewById(R.id.notesDate);
        notesContent = findViewById(R.id.notesContent);
        backButton = findViewById(R.id.backButton);

        var noteId = intent.getIntExtra("noteId",-1);
        var title = intent.getStringExtra("title");
        var content = intent.getStringExtra("content");
        var date = intent.getStringExtra("date");

        backButton.setOnClickListener{
            finish();
        }

        if(noteId == -1){
            finish();
        }else{
            notesTitle.text = title;
            notesDate.text = date;
            notesContent.text = content;
        }
    }
}