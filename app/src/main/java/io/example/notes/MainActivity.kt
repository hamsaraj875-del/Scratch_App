package io.example.notes

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import io.example.notes.repository.DatabaseRepository
import io.example.notes.room.AppDatabase
import io.example.notes.room.User
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val userNameText: TextView;


        userNameText = findViewById(R.id.userName);

        val getIntent = getIntent();
        var userId = getIntent.getLongExtra("userId",-1);

        var db = AppDatabase.getInstance(applicationContext);
        var repository = DatabaseRepository(db.userDao());

        if(userId == -1L){
            var i = Intent(this,LoginActivity::class.java);
            startActivity(i);
            return;
        }else{

            lifecycleScope.launch{
                val userData = repository.getUserById(userId);
                if (userData != null) {
                    userNameText.text = userData.name;
                }
            }
        }
    }
}