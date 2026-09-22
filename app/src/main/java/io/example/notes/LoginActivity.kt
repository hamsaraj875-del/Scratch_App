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
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var userEmail : TextInputEditText;
        var userPassword : TextInputEditText;
        var btnLogin: MaterialButton;
        var redirectSign:MaterialButton;

        var db = AppDatabase.getInstance(applicationContext);
        var repository = DatabaseRepository(db.userDao(), db.noteDao());

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        btnLogin = findViewById(R.id.btnLogin);
        redirectSign = findViewById(R.id.redirectSign);

        redirectSign.setOnClickListener{
            var intent = Intent(this@LoginActivity,SignupActivity::class.java);
            startActivity(intent);
        }

        btnLogin.setOnClickListener{
            var email = userEmail.text.toString();
            var password = userPassword.text.toString();
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this@LoginActivity,"Fields cannot be empty !",Toast.LENGTH_SHORT).show();
            }else{
                var id:Long=0;
                lifecycleScope.launch{

                    var user = repository.getUserByEmail(email);

                    if(user==null){
                        Toast.makeText(this@LoginActivity,"Account not exist please sign up first !",Toast.LENGTH_SHORT).show();
                    }else{
                        if(user.email == email && user.password == password){
                            Toast.makeText(this@LoginActivity,"Login successfull"+user.name,Toast.LENGTH_SHORT).show();
                            var intent = Intent(this@LoginActivity,MainActivity::class.java);
                            startActivity(intent);
                        }else{
                            Toast.makeText(this@LoginActivity,"Invalid credentials please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        }

    }
}