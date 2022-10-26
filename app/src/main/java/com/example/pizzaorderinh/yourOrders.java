package com.example.pizzaorderinh;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class yourOrders extends Fragment {

   FirebaseDatabase fOrder = FirebaseDatabase.getInstance();
   String ordUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
   DatabaseReference dYourOrder = fOrder.getReference().child("Orders").child(ordUser);
   yourOrderAdapter yoa;
   RecyclerView recOrder;
   ProgressDialog cancelling;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public yourOrders() {
        // Required empty public constructor
    }


    public static yourOrders newInstance(String param1, String param2) {
        yourOrders fragment = new yourOrders();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentView
        View viewOrder = inflater.inflate(R.layout.fragment_your_orders, container, false);
        TextView yoLabel = viewOrder.findViewById(R.id.yourOrderLabelText);
        yoLabel.setPaintFlags(yoLabel.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        Button cancel = viewOrder.findViewById(R.id.cancOrder);
        recOrder = viewOrder.findViewById(R.id.orderRec);
        recOrder.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<orderItem> optionsO =
                new FirebaseRecyclerOptions.Builder<orderItem>()
                        .setQuery(dYourOrder, orderItem.class)
                        .build();
        yoa = new yourOrderAdapter(optionsO);
        recOrder.setAdapter(yoa);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dYourOrder.addValueEventListener(new ValueEventListener() {
                    public void cancellation() {
                        cancelling.setTitle("Cancel Order");
                        cancelling.setMessage("Your Order is Being Cancelled");
                        cancelling.show();
                    }
                    public void sendUserToMenu(){
                        AppCompatActivity ordersSeVapas = (AppCompatActivity)getContext();
                        ordersSeVapas.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new recFragment()).addToBackStack(null).commit();
                    }
                    public void sendUserToYourOrders(){
                        AppCompatActivity menuSeOrder = (AppCompatActivity)getContext();
                        menuSeOrder.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new yourOrders()).addToBackStack(null).commit();
                    }

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dYourOrder.setValue(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return  viewOrder;
    }
    @Override
    public void onStart() {
        super.onStart();
        yoa.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        yoa.stopListening();
    }
}