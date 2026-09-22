package io.example.notes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import io.example.notes.repository.DatabaseRepository
import io.example.notes.room.AppDatabase
import io.example.notes.room.Note
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewRoom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_room)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var noteTitle : TextInputEditText;
        var noteContent:TextInputEditText;
        var btnSave: Button;


        //database management
        var db = AppDatabase.getInstance(applicationContext);
        var repository = DatabaseRepository(db.userDao(), db.noteDao());

        var userId = intent.getIntExtra("userId",0);

        noteTitle = findViewById(R.id.noteTitle);
        noteContent = findViewById(R.id.noteContent);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener{
            var title = noteTitle.text.toString();
            var content = noteContent.text.toString();
            if(title.isEmpty() || content.isEmpty()){
                Toast.makeText(this@NewRoom,"Fields cannot be empty !",Toast.LENGTH_SHORT).show();
            }else{
                val date = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(Date())
                var pinned = 0;
                lifecycleScope.launch {
                    repository.insertNote(Note(userId = userId, title = title, content = content, date = date,pinned=pinned));

                    Toast.makeText(
                        this@NewRoom,
                        "Note saved",
                        Toast.LENGTH_SHORT
                    ).show();
                    finish();
                }
            }
        }
    }

}