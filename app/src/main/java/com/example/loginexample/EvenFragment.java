package com.example.loginexample;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EvenFragment extends Fragment {

    private DBHelper DB;
    private ArrayList<String> name, email, password;
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    public EvenFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = new DBHelper(getContext());
        name = new ArrayList<>();
        email = new ArrayList<>();
        password = new ArrayList<>();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_even, container, false);
        recyclerView = view.findViewById(R.id.rv_even);
        adapter = new MyAdapter(getContext(), name, email, password);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        displayEvenData();

        return view;
    }


    private void displayEvenData() {
        Cursor cursor = DB.getData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No entry exists", Toast.LENGTH_SHORT).show();
            return;
        } else {
            int position = 0;
            while (cursor.moveToNext()) {
                if (position % 2 == 0) {
                    name.add(cursor.getString(0));
                    email.add(cursor.getString(1));
                    password.add(cursor.getString(2));
                }
                position++;
            }
            adapter.notifyDataSetChanged();
        }
    }
}

