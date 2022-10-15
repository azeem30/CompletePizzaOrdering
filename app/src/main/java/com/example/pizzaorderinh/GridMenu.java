package com.example.pizzaorderinh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class GridMenu extends AppCompatActivity {

GridView grid;
    int pImage[]={R.drawable.pissaladiere,
            R.drawable.margerhita,
            R.drawable.capsicum,
            R.drawable.chebusrt,
            R.drawable.napoletana,
            R.drawable.pepperoni,
            R.drawable.newyork,
            R.drawable.romana,
            R.drawable.sourdough,
            R.drawable.vegch,
    };
    String pizzaName[]={"Pissaladiere",
            "Margerhita",
            "Capsicum & Onion",
            "Cheese Burst",
            "Napoletana",
            "Pepperoni",
            "New-York Styled",
            "Romana",
            "Sourdough",
            "Veg-Cheese"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_menu);
        grid = (GridView) findViewById(R.id.gView);
        grid.setAdapter(new GridAdapter(this,pizzaName,pImage));
    }
}