package com.example.loginexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class RegistrationActivity extends AppCompatActivity {

    private EditText mFullNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        mFullNameEditText = findViewById(R.id.txtName);
        mEmailEditText = findViewById(R.id.txtEmail);
        mPasswordEditText = findViewById(R.id.txtPwd);
        mDbHelper = new DBHelper(this);

        Button signUpButton = findViewById(R.id.btnLogin);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mFullNameEditText.getText().toString().trim();
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();

                if (!isValidFullName(name)) {
                    mFullNameEditText.setError("Full name must have only characters");
                } else if (!isValidEmail(email)) {
                    mEmailEditText.setError("Invalid email address");
                } else if (!isValidPassword(password)) {
                    mPasswordEditText.setError("Password must have minimum 8 characters");
                } else {
                    long newRowId = addStudent(name, email, password);

                    if (newRowId == -1) {
                        Toast.makeText(RegistrationActivity.this, "Error saving user", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegistrationActivity.this, "User saved with id: " + newRowId, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        TextView login = findViewById(R.id.lnkLogin);
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isValidFullName(CharSequence target) {
        return !TextUtils.isEmpty(target) && TextUtils.getTrimmedLength(target) > 0 && target.toString().matches("[a-zA-Z ]+");
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private long addStudent(String fullName, String email, String password) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_NAME, fullName);
        values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, email);
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, password);
        long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}
