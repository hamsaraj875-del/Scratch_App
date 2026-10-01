package io.example.notes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
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
        var btnEditProfile : LinearLayout
        var btnChangePassword :LinearLayout
        var accountName:TextView;
        var accountEmail: TextView
        var infoName:TextView;
        var infoEmail:TextView;


        btnLogout = findViewById(R.id.btnLogout);
        accountName = findViewById(R.id.accountName);
        accountEmail = findViewById(R.id.accountEmail);
        infoName = findViewById(R.id.infoName);
        infoEmail = findViewById(R.id.infoEmail);
        btnEditProfile = findViewById(R.id.btnEditProfile);


        var db = AppDatabase.getInstance(applicationContext);
        var repository = DatabaseRepository(db.userDao(), db.noteDao());

        var userId = intent.getIntExtra("userId",-1);
        if(userId==-1){
            finish();
        }else{
            lifecycleScope.launch{
                repository.getUserById(userId).observe(this@Account){user->
                    accountName.text = user.name;
                    accountEmail.text = user.email;
                    infoName.text = user.name;
                    infoEmail.text = user.email;
                }
            }
        }


        btnLogout.setOnClickListener{
            logout(repository,userId);
        }

        btnEditProfile.setOnClickListener{
            editProfile(userId);
        }


    }
    fun logout(repository:DatabaseRepository,userId:Int){
        lifecycleScope.launch{
            var logout = repository.logout(userId);
            if(logout){
                Toast.makeText(this@Account,"Logout Successfull", Toast.LENGTH_SHORT).show();
                var intent = Intent(this@Account, LoginActivity::class.java);
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                )

                startActivity(intent)
                finishAffinity();
            }else{
                Toast.makeText(this@Account,"Logout unsuccessfull ",Toast.LENGTH_SHORT).show();
            }
        }
    }

    fun editProfile(userId:Int){
        var intent = Intent(this@Account,EditProfile::class.java);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }
}