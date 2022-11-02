package com.example.pizzaorderinh;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link trollyList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class trollyList extends Fragment {

    FirebaseDatabase fire = FirebaseDatabase.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String user = fAuth.getCurrentUser().getUid();
    DatabaseReference dRef = fire.getReference().child("Cart").child(user);
    RecyclerView cartRecycler;
    cartAdapter ca;
    List priceList;
    int cp,totCost;
    int sum=0;
    int current;
    String total;
    HashMap <String,String> orderMap;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public trollyList() {
        // Required empty public constructor
    }

    public static trollyList newInstance(String param1, String param2) {
        trollyList fragment = new trollyList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewCart =inflater.inflate(R.layout.fragment_trolly_list, container, false);
        ScrollView cScroll =viewCart.findViewById(R.id.cartScroll);
        ImageView clogo = viewCart.findViewById(R.id.cartListLogo);
        TextView cartText=viewCart.findViewById(R.id.cartListText);
        TextView totalCost = viewCart.findViewById(R.id.actTotCost);
        Button checkout = viewCart.findViewById(R.id.checkout);
        checkout.setPaintFlags(checkout.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        cartText.setPaintFlags(cartText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        cScroll.setSmoothScrollingEnabled(true);
        cartRecycler=viewCart.findViewById(R.id.recCartList);
        cartRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<cartItem> optionsT =
                new FirebaseRecyclerOptions.Builder<cartItem>()
                        .setQuery(dRef, cartItem.class)
                        .build();
        ca= new cartAdapter(optionsT);
        cartRecycler.setAdapter(ca);

        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                priceList = new ArrayList<Integer>();
                for(DataSnapshot tds : snapshot.getChildren()){
                    String uKey = tds.getKey();
                    String cartPrice = tds.child("orderTotal").getValue().toString();
                    cp = Integer.parseInt(cartPrice);
                    priceList.add(cp);
                }

                for(int i = 0 ; i<priceList.size();i++){
                    current = (int) priceList.get(i);
                    sum = sum + current;
                }
                totCost = sum;
                total = String.valueOf(totCost);
                totalCost.setText(total);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       checkout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             AppCompatActivity confirmKaroOrder = (AppCompatActivity)  getContext();
             confirmKaroOrder.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
             confirmKaroOrder.getSupportFragmentManager().beginTransaction().replace(R.id.start, new confirmOrder()).addToBackStack(null).commit();
           }
       });


        return viewCart;
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