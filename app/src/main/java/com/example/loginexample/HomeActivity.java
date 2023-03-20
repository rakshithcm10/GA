package com.example.loginexample;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    ArrayList<String> name, email, password;
    DBHelper DB;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        DB = new DBHelper(this);
        name = new ArrayList<>();
        email = new ArrayList<>();
        password = new ArrayList<>();


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ViewPager viewPager = findViewById(R.id.viewPager);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TabLayout tabLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);


        displayData();


        sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        Button logoutButton = findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void displayData() {
        Cursor cursor = DB.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(HomeActivity.this, "No entries exist", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
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

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new EvenFragment();
                case 1:
                    return new OddFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Odd Names";
                case 1:
                    return "Even Names";
                default:
                    return null;
            }
        }
    }
}

