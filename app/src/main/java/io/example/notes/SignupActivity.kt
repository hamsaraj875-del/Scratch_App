package io.example.notes

import android.content.Intent
import android.os.Bundle
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
import io.example.notes.room.User
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var btnSignUp: MaterialButton;
        var redirectLogin:MaterialButton;
        var userName: TextInputEditText;
        var userEmail:TextInputEditText;
        var userPassword:TextInputEditText;

        btnSignUp = findViewById(R.id.btnSignup);
        redirectLogin = findViewById(R.id.redirectLogin);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);


        var db = AppDatabase.getInstance(applicationContext);
        var repository = DatabaseRepository(db.userDao(), db.noteDao());



        redirectLogin.setOnClickListener{
            var intent = Intent(this@SignupActivity,LoginActivity::class.java);
            startActivity(intent);
        }

        btnSignUp.setOnClickListener{
            var name = userName.text.toString();
            var email = userEmail.text.toString();
            var password = userPassword.text.toString();
            if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this@SignupActivity,"Fields cannot be empty",Toast.LENGTH_SHORT).show();
            }else{
                lifecycleScope.launch {
                    var user = repository.getUserByEmail(email);
                    if(user==null){
                        var id = repository.insert(
                            User(
                                name = name,
                                email = email,
                                password = password,
                                loggedIn = 1,
                            )
                        );
                        Toast.makeText(
                            this@SignupActivity,
                            "Sign up is successfull",
                            Toast.LENGTH_SHORT
                        ).show();
                        var intent = Intent(this@SignupActivity, MainActivity::class.java);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this@SignupActivity,"Account already exist please login",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
    }
}