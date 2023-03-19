package com.example.loginexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    ArrayList<String> name,email,password;
    DBHelper DB;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DB=new DBHelper(this);
        name=new ArrayList<>();
        email=new ArrayList<>();
        password=new ArrayList<>();
        recyclerView =findViewById(R.id.recyclerview);
        adapter=new MyAdapter(this,name,email,password);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();

        sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);

        Button logoutButton = findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void displaydata() {
        Cursor cursor=DB.getData();
        if(cursor.getCount()==0){
            Toast.makeText(HomeActivity.this,"No entry exists",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while(cursor.moveToNext())
            {
                name.add(cursor.getString(0));
                email.add(cursor.getString(1));
                password.add(cursor.getString(2));
            }
        }
    }

    private void logout() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.apply();


        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
