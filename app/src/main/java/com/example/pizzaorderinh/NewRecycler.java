package com.example.pizzaorderinh;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        MenuItem mi = menu.findItem(R.id.search);
        SearchView sv = (SearchView) mi.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String s)
    {
        FirebaseRecyclerOptions<fbItem> options =
                new FirebaseRecyclerOptions.Builder<fbItem>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Pizzas").orderByChild("pName").startAt(s.toUpperCase()).endAt(s.toLowerCase()+"\uf8ff"), fbItem.class)
                        .build();

        fba = new fbAdapter(options);
        fba.startListening();
        RecyclerView recView = findViewById(R.id.rec);
        recView.setAdapter(fba);
    }
}