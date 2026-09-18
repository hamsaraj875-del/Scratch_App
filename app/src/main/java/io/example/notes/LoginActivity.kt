package io.example.notes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        var userName : EditText;
        var userEmail :EditText;
        var userPassword : EditText;
        var btnLogin: Button;


        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener{
            var name = userName.text.toString();
            var email = userEmail.text.toString();
            var password = userPassword.text.toString();
            if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Fields cannot be empty !",Toast.LENGTH_SHORT).show();
            }else{
                var intent = Intent(this, MainActivity::class.java);
                intent.putExtra("name",name);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        }

    }
}