package com.example.pizzaorderinh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


     List<Item> items;
     MyAdapter ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchView sv =findViewById(R.id.search);
        sv.clearFocus();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        RecyclerView rc = findViewById(R.id.recycle);
        List<Item> items = new ArrayList<>();
        items.add(new Item("Pissaladiere",R.drawable.pissaladiere));
        items.add(new Item("Veg Cheese",R.drawable.vegch));
        items.add(new Item("Margerhita",R.drawable.margerhita));
        items.add(new Item("Pepperoni",R.drawable.pepperoni));
        items.add(new Item("Sourdough",R.drawable.sourdough));
        items.add(new Item("Napoletana",R.drawable.napoletana));
        items.add(new Item("Romana",R.drawable.romana));
        items.add(new Item("New-York Styled",R.drawable.newyork));
        items.add(new Item("Capsicum Onion",R.drawable.capsicum));
        items.add(new Item("Cheese Burst",R.drawable.chebusrt));



        rc.setLayoutManager(new LinearLayoutManager(this));
        rc.setAdapter(new MyAdapter(getApplicationContext(),items));


    }

    private void filterList(String text) {
        List<Item> fl = new ArrayList<>();
        for(Item item: items)
        {
            if(item.getPizza().toLowerCase().contains(text.toLowerCase()))
            {
                fl.add(item);
            }
            if(fl.isEmpty())
            {
                Toast.makeText(this, "NO DATA FOUND", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ma.setfl(fl);
            }
        }
    }


}