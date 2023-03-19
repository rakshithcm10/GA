package com.example.loginexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerTextView;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.txtEmail);
        passwordEditText = findViewById(R.id.txtPwd);
        loginButton = findViewById(R.id.btnLogin);
        registerTextView = findViewById(R.id.lnkRegister);

        sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid email address");
                    emailEditText.requestFocus();
                    return;
                }

                if (!isValidPassword(password)) {
                    passwordEditText.setError("Password must be at least 8 characters");
                    passwordEditText.requestFocus();
                    return;
                }

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();
                    navigateToHomeScreen();
                    finish();
                }

            }
        });


        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


        String email = sharedPreferences.getString("email", null);
        String password = sharedPreferences.getString("password", null);

        if (email == null || password == null) {
            emailEditText.setText(getIntent().getStringExtra("email"));
            passwordEditText.setText(getIntent().getStringExtra("password"));
        } else {
            navigateToHomeScreen();
            finish();
        }
    }

    private void navigateToHomeScreen() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

}



