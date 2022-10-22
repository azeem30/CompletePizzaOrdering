package com.example.pizzaorderinh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cart extends AppCompatActivity {

FirebaseDatabase fire = FirebaseDatabase.getInstance();
FirebaseAuth fAuth = FirebaseAuth.getInstance();
String user = fAuth.getCurrentUser().getUid();
DatabaseReference dRef = fire.getReference().child("Cart").child(user);
RecyclerView cartRecycler;
cartAdapter ca;
LinearLayoutManager cartL;
TextView cartText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartText=findViewById(R.id.cartText);
        cartRecycler=findViewById(R.id.recCart);
        cartL = new LinearLayoutManager(this);
        cartRecycler.setLayoutManager(cartL);
        cartText.setPaintFlags(cartText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        FirebaseRecyclerOptions<cartItem> optionsC =
                new FirebaseRecyclerOptions.Builder<cartItem>()
                        .setQuery(dRef, cartItem.class)
                        .build();
        ca= new cartAdapter(optionsC);
        cartRecycler.setAdapter(ca);


    }
    @Override
    public void onStart() {
        super.onStart();
        ca.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        ca.stopListening();
    }

}