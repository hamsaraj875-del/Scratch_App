package io.example.notes

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.example.notes.repository.DatabaseRepository
import io.example.notes.room.AppDatabase
import io.example.notes.room.NoteDao
import io.example.notes.room.UserDao
import kotlinx.coroutines.launch

class EditProfile: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var editName : TextInputEditText;
        var editEmail :TextInputEditText;
        var cancelButton : MaterialButton;
        var saveChangesButton : MaterialButton;
        var backButton : ImageView;
        var userId = -1;

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editName);
        cancelButton = findViewById(R.id.cancelButton);
        backButton = findViewById(R.id.backButton);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        var db = AppDatabase.getInstance(applicationContext);
        var repository = DatabaseRepository(db.userDao(), db.noteDao());


        cancelButton.setOnClickListener{
            finish();
        }
        backButton.setOnClickListener{
            finish();
        }

        userId = intent.getIntExtra("userId",-1);

        if(userId == -1){
            finish();
        }

        saveChangesButton.setOnClickListener{
            var name = editName.text.toString();
            var email = editEmail.text.toString();
            if(name.isEmpty() or email.isEmpty()){
                Toast.makeText(this@EditProfile,"Feilds cannot be empty",Toast.LENGTH_SHORT).show();
            }else{
                if(userId!=-1){
                    lifecycleScope.launch{
                        repository.updateProfile(userId,name,email);
                    }
                    Toast.makeText(this@EditProfile,"Changes saved successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }


}