
package io.example.notes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.example.notes.repository.DatabaseRepository
import io.example.notes.room.AppDatabase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //variable declarations
        val profileIcon: TextView;
        val newRoomButton: Button;
        var userId:Int=0;


        //database handlers
        var db = AppDatabase.getInstance(applicationContext);
        var repository = DatabaseRepository(db.userDao(), db.noteDao());


        //variable intializations
        newRoomButton = findViewById(R.id.newRoomButton);
        profileIcon = findViewById(R.id.profileIcon)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val emptyNotesLayout = findViewById<LinearLayout>(R.id.emptyNotesLayout)


        //new note creation
        newRoomButton.setOnClickListener{
            noteCreation(userId);
        }

        lifecycleScope.launch {
            var user = repository.getLoggedInUser();
            if (user == null) {
                var intent = Intent(this@MainActivity, LoginActivity::class.java);
                startActivity(intent);
                finish();
            } else {
                userId = user.id;
                var userNotes = repository.getAllNotes(user.id);
                if(userNotes.isEmpty()){
                    recyclerView.visibility = View.GONE;
                    emptyNotesLayout.visibility = View.VISIBLE;
                }else{
                    emptyNotesLayout.visibility = View.GONE;
                    recyclerView.visibility = View.VISIBLE;
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    val adapter = NoteAdapter(userNotes)
                    recyclerView.adapter = adapter
                }
                profileIcon.text = user.name?.get(0).toString();
                Toast.makeText(this@MainActivity, "Welcome " + user.name, Toast.LENGTH_SHORT).show();
            }
        }
    }

    fun noteCreation(userId:Int){
        var intent = Intent(this@MainActivity,NewRoom::class.java);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }

}