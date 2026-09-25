package io.example.notes

import android.os.Bundle
import android.widget.Button
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

class Account : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var btnLogout : Button;
        var user: User;


        btnLogout = findViewById(R.id.btnLogout);
        var db = AppDatabase.getInstance(applicationContext);
        var repository = DatabaseRepository(db.userDao(), db.noteDao());

        var userId = intent.getIntExtra("userId",-1);
        if(userId==1){
            finish();
        }else{
            lifecycleScope.launch{
                user = repository.getUserById(userId);
            }
        }


        btnLogout.setOnClickListener{
            lifecycleScope.launch{
                var logout = repository.logout(userId);
                if(logout){
                    Toast.makeText(this@Account,"Logout Successfull", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this@Account,"Logout unsuccessfull ",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}