
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
        val profileIcon:TextView;
        profileIcon = findViewById(R.id.profileIcon)
        var db = AppDatabase.getInstance(applicationContext);
        var repository = DatabaseRepository(db.userDao());

        lifecycleScope.launch {
            var user = repository.getLoggedInUser();
            if (user == null) {
                var intent = Intent(this@MainActivity, LoginActivity::class.java);
                startActivity(intent);
                finish();
            } else {
                profileIcon.text = user.name?.get(0).toString();
                Toast.makeText(this@MainActivity,"Welcome "+user.name,Toast.LENGTH_SHORT).show();
            }
        }
    }
}