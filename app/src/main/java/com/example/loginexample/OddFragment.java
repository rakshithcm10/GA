package com.example.loginexample;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OddFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> name, email, password;
    DBHelper DB;
    MyAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_odd, container, false);

        DB = new DBHelper(getContext());
        name = new ArrayList<>();
        email = new ArrayList<>();
        password = new ArrayList<>();

        recyclerView = view.findViewById(R.id.rv_odd);
        adapter = new MyAdapter(getContext(), name, email, password);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        displayData();

        return view;
    }


    private void displayData() {
        Cursor cursor = DB.getData();
        if (cursor.getCount() == 0) {
            return;
        } else {
            while (cursor.moveToNext()) {
                int position = cursor.getPosition();
                if (position % 2 != 0) {
                    name.add(cursor.getString(0));
                    email.add(cursor.getString(1));
                    password.add(cursor.getString(2));
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}

