package io.example.notes

import android.os.Bundle
import android.text.InputType
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.example.notes.repository.DatabaseRepository
import io.example.notes.room.AppDatabase
import io.example.notes.room.NoteDao
import io.example.notes.room.User
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
        var editPassword : TextInputEditText;
        var changeNameButton : MaterialButton;
        var changeEmailButton : MaterialButton;
        var changePasswordButton : MaterialButton;
        var user:User?=null;


        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        changeNameButton = findViewById(R.id.changeNameButton);
        changeEmailButton = findViewById(R.id.changeEmailButton);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        var db = AppDatabase.getInstance(applicationContext);
        var repository = DatabaseRepository(db.userDao(),db.noteDao());

        var userId = intent.getIntExtra("userId",-1);

        if(userId==-1){
            finish();
        }

        lifecycleScope.launch{
            user = repository.getUser(userId);
        }
;

        changePasswordButton.setOnClickListener{
            if(editPassword.toString().isEmpty()){
                Toast.makeText(this@EditProfile,"Password field cannot be empty",Toast.LENGTH_SHORT).show();
            }else if(editPassword.toString().equals(user?.password)){
                Toast.makeText(this@EditProfile,"Password is same",Toast.LENGTH_SHORT).show();
            }else{
                passwordHandler(userId,editPassword.toString(),repository);
            }
        }

        changeEmailButton.setOnClickListener{
            if(editEmail.toString().isEmpty()){
                Toast.makeText(this@EditProfile,"Email field cannot be empty",Toast.LENGTH_SHORT).show();
            }else if(editEmail.toString().equals(user?.password)){
                Toast.makeText(this@EditProfile,"Password is same",Toast.LENGTH_SHORT).show();
            }else{
                emailHandler(userId,editEmail.toString(),repository);
            }
        }

        changeNameButton.setOnClickListener{
            if(editName.toString().isEmpty()){
                Toast.makeText(this@EditProfile,"Name field cannot be empty",Toast.LENGTH_SHORT).show();
            }else if(editName.toString().equals(user?.name)){
                Toast.makeText(this@EditProfile,"Password is same",Toast.LENGTH_SHORT).show();
            }else{
                nameHandler(userId,editName.toString(),repository);
            }
        }
    }

    fun passwordHandler(userId:Int,newPassword:String,repository:DatabaseRepository){
        lifecycleScope.launch{
            var v = repository.updatePassword(userId,newPassword);
            Toast.makeText(this@EditProfile,"Password Updated Successfully",Toast.LENGTH_SHORT).show();
        }
    }

    fun emailHandler(userId:Int,email:String,repository:DatabaseRepository){
        lifecycleScope.launch{
            var v = repository.updateEmail(userId,email);
            Toast.makeText(this@EditProfile,"Email Updated Successfully",Toast.LENGTH_SHORT).show();
        }
    }

    fun nameHandler(userId:Int,name:String,repository:DatabaseRepository){
        lifecycleScope.launch{
            var v = repository.updateName(userId,name);
            Toast.makeText(this@EditProfile,"Name Updated Successfully",Toast.LENGTH_SHORT).show();
        }
    }
}