
package io.example.notes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.example.notes.repository.DatabaseRepository
import io.example.notes.room.AppDatabase
import io.example.notes.room.Note
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
        var recyclerView: RecyclerView
        var pinnedRecyclerView: RecyclerView
        var pinnedHeader: LinearLayout;
        var homeContent : LinearLayout;
        var Loader : ProgressBar;
        var notesScrollView : NestedScrollView;
        var userId:Int=0;


        //database handlers
        var db = AppDatabase.getInstance(applicationContext);
        var repository = DatabaseRepository(db.userDao(), db.noteDao());


        //variable intializations
        newRoomButton = findViewById(R.id.newRoomButton);
        profileIcon = findViewById(R.id.profileIcon)
        recyclerView = findViewById(R.id.recyclerView)
        pinnedRecyclerView = findViewById(R.id.pinnedRecyclerView)
        homeContent  = findViewById(R.id.homeContent);
        Loader = findViewById(R.id.progressBar);
        pinnedHeader = findViewById(R.id.pinnedHeader);
        notesScrollView = findViewById(R.id.notesScrollView);
        val emptyNotesLayout = findViewById<LinearLayout>(R.id.emptyNotesLayout)


        profileIcon.setOnClickListener{
            accountDetails(userId);
        }


        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        pinnedRecyclerView.layoutManager =
            LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )

        val adapter = UnpinnedNote(
            emptyList(),
            { note ->
                deleteNote(note, repository)
            },
            { note-> updatePin(note,repository,1);},
            { note ->
                showNoteReader(note)
            }
        )
        val pinnedAdapter = PinnedNote(
            emptyList(),
            { note -> updatePin(note, repository, 0) },
            { note -> showNoteReader(note) }
        )

        recyclerView.adapter = adapter;
        pinnedRecyclerView.adapter = pinnedAdapter;

        newRoomButton.setOnClickListener{
            noteCreation(userId);
        }

        lifecycleScope.launch {
            Loader.visibility = View.VISIBLE;
            homeContent.visibility = View.GONE;
            newRoomButton.visibility = View.GONE;
            notesScrollView.visibility = View.GONE;
            pinnedRecyclerView.visibility = View.GONE
            pinnedHeader.visibility = View.GONE;
            var user = repository.getLoggedInUser();
            var users = repository.getAllUser();
            if(users == null || users.isEmpty()){
                var intent = Intent(this@MainActivity,AppPromo::class.java);
                startActivity(intent);
                finish();
            }
            else if (user == null) {
                var intent = Intent(this@MainActivity, LoginActivity::class.java);
                startActivity(intent);
                finish();
            } else {
                userId = user.id;
                Loader.visibility = View.GONE
                homeContent.visibility = View.VISIBLE;
                newRoomButton.visibility = View.VISIBLE;
                notesScrollView.visibility = View.VISIBLE;
                repository.getUserById(userId).observe(this@MainActivity) { updatedUser ->
                    if (updatedUser != null) {
                        profileIcon.text = updatedUser.name
                            ?.firstOrNull()
                            ?.uppercase()
                            ?: ""
                    }
                }
                repository.getAllNotes(user.id).observe(this@MainActivity) { userNotes ->
                    if (userNotes.isEmpty()) {
                        recyclerView.visibility = View.GONE
                        emptyNotesLayout.visibility = View.VISIBLE
                    } else {
                        emptyNotesLayout.visibility = View.GONE
                        val pinnedNotes = userNotes.filter { it.pinned==1 }
                        val normalNotes = userNotes.filter { it.pinned==0 }
                        if (pinnedNotes.isEmpty()) {
                            pinnedRecyclerView.visibility = View.GONE;
                            pinnedHeader.visibility = View.GONE;
                        } else {
                            pinnedRecyclerView.visibility = View.VISIBLE
                            pinnedHeader.visibility = View.VISIBLE;
                            pinnedAdapter.updateNotes(pinnedNotes)
                        }
                        if (normalNotes.isEmpty()) {
                            recyclerView.visibility = View.GONE
                        } else {
                            recyclerView.visibility = View.VISIBLE
                            adapter.updateNotes(normalNotes)
                        }
                    }
                }
                Toast.makeText(this@MainActivity, "Welcome " + user.name, Toast.LENGTH_SHORT).show();
            }
        }
    }

    fun noteCreation(userId:Int){
        var intent = Intent(this@MainActivity,NewRoom::class.java);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }

    fun showNoteReader(note:Note){
        var intent = Intent(this@MainActivity,NotesReader::class.java);
        intent.putExtra("noteId",note.id);
        intent.putExtra("title",note.title);
        intent.putExtra("content",note.content);
        intent.putExtra("date",note.date);
        startActivity(intent);
    }

    fun deleteNote(note:Note,repository:DatabaseRepository){
        lifecycleScope.launch{
            repository.deleteNote(note);
            Toast.makeText(this@MainActivity,note.title + " Deleted Successfully ",Toast.LENGTH_SHORT).show();
        }
    }

    fun updatePin(note:Note,repository:DatabaseRepository,pin:Int){
        lifecycleScope.launch{
            var value = repository.updatePin(note.id,pin);
            if(value && pin==1){
                var noteName = note.title;
                Toast.makeText(this@MainActivity,"$noteName pinned",Toast.LENGTH_SHORT).show();
            }else if(value && pin==0){
                var noteName = note.title;
                Toast.makeText(this@MainActivity,"$noteName unpinned",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this@MainActivity,"Error occured please try again",Toast.LENGTH_SHORT).show();
            }
        }
    }

    fun accountDetails(userId:Int){
        var intent = Intent(this@MainActivity,Account::class.java);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }

}