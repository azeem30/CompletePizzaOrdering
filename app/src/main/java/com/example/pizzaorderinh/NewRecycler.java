package com.example.pizzaorderinh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class NewRecycler extends AppCompatActivity {
fbAdapter fba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recycler);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        RecyclerView recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<fbItem> options =
                new FirebaseRecyclerOptions.Builder<fbItem>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Pizzas"), fbItem.class)
                        .build();
        fba = new fbAdapter(options);

        recyclerView.setAdapter(fba);
    }
    @Override
    protected void onStart() {
        super.onStart();
        fba.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        fba.stopListening();
    }

}