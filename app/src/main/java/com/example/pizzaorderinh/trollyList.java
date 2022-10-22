package com.example.pizzaorderinh;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        ImageView clogo = viewCart.findViewById(R.id.cartListLogo);
        TextView cartText=viewCart.findViewById(R.id.cartListText);
        cartText.setPaintFlags(cartText.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        cartRecycler=viewCart.findViewById(R.id.recCartList);
        cartRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<cartItem> optionsT =
                new FirebaseRecyclerOptions.Builder<cartItem>()
                        .setQuery(dRef, cartItem.class)
                        .build();
        ca= new cartAdapter(optionsT);
        cartRecycler.setAdapter(ca);

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