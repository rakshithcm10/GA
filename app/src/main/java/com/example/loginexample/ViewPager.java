package com.example.loginexample;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ViewPager extends AppCompatActivity {
    ArrayList<String> name, email, password;
    DBHelper DB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        DB = new DBHelper(this);
        name = new ArrayList<>();
        email = new ArrayList<>();
        password = new ArrayList<>();


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) androidx.viewpager.widget.ViewPager viewPager = findViewById(R.id.viewPager);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TabLayout tabLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(new HomeActivity.ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        displayData();

    }

        private void displayData() {
            Cursor cursor = DB.getData();
            if (cursor.getCount() == 0) {
                Toast.makeText(ViewPager.this, "No entries exist", Toast.LENGTH_SHORT).show();
                return;
            } else {
                while (cursor.moveToNext()) {
                    name.add(cursor.getString(0));
                    email.add(cursor.getString(1));
                    password.add(cursor.getString(2));
                }
            }
        }
    public void onBackPressed() {
        super.onBackPressed();
        // Navigate back to HomeActivity
        finish(); // This will close the current activity (ViewPager)
    }
    }

