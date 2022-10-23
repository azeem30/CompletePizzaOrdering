package com.example.pizzaorderinh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.Value;

import java.util.HashMap;


public class trollyDetails extends Fragment {

    FirebaseDatabase fiery = FirebaseDatabase.getInstance();
    FirebaseAuth cAuth = FirebaseAuth.getInstance();
    String cUser = cAuth.getCurrentUser().getUid();
    DatabaseReference dFire = fiery.getReference().child("Cart").child(cUser);
    ProgressDialog changes,deleting;



    String quantity,cpImage,cpName,orderTotal;
    ImageView trimg1,minus,plus,delete;
    TextView trtex1,trtex2,trtex3;
    Button saveNew;
    String stoldStore,stnewStore,stnayaQuantity,stnewOrderTotal;
    int operate,assign,newQuant,oldQuant,perPieceCost,intOrderTotal,intNewOrderTotal;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public trollyDetails() {
        // Required empty public constructor
    }

    public trollyDetails(String quantity, String cpImage, String cpName, String orderTotal) {
        this.quantity = quantity;
        this.cpImage = cpImage;
        this.cpName = cpName;
        this.orderTotal = orderTotal;
    }

    public static trollyDetails newInstance(String param1, String param2) {
        trollyDetails fragment = new trollyDetails();
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
        View viewCartDetails =inflater.inflate(R.layout.fragment_trolly_details, container, false);
        trimg1 = viewCartDetails.findViewById(R.id.trollyI);
        trtex1 = viewCartDetails.findViewById(R.id.trollyN);
        trtex2 = viewCartDetails.findViewById(R.id.trollyP);
        trtex3 = viewCartDetails.findViewById(R.id.textQuan);
        minus = viewCartDetails.findViewById(R.id.decre);
        plus = viewCartDetails.findViewById(R.id.incre);
        delete = viewCartDetails.findViewById(R.id.delbin);
        saveNew = viewCartDetails.findViewById(R.id.saveChange);
        changes = new ProgressDialog(getContext());

        trtex1.setText(cpName);
        trtex2.setText(orderTotal);
        trtex3.setText(quantity);
        Glide.with(getContext()).load(cpImage).into(trimg1);

        oldQuant = Integer.parseInt(quantity);
        intOrderTotal = Integer.parseInt(orderTotal);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stoldStore = trtex3.getText().toString();
                operate = Integer.parseInt(stoldStore);
                assign = operate-1;
                stnewStore = String.valueOf(assign);
                trtex3.setText(stnewStore);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stoldStore = trtex3.getText().toString();
                operate = Integer.parseInt(stoldStore);
                assign = operate+1;
                stnewStore = String.valueOf(assign);
                trtex3.setText(stnewStore);

            }
        });

        saveNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stnayaQuantity = trtex3.getText().toString();
                newQuant = Integer.parseInt(stnayaQuantity);
                perPieceCost = intOrderTotal/oldQuant;
                intNewOrderTotal = perPieceCost*newQuant;
                stnewOrderTotal = String.valueOf(intNewOrderTotal);

                if(newQuant==0||intNewOrderTotal==0){

                }

                HashMap<String,String> changedCart = new HashMap<>();
                changedCart.put("cpImage",cpImage);
                changedCart.put("cpName",cpName);
                changedCart.put("orderTotal",stnewOrderTotal);
                changedCart.put("quantity",stnayaQuantity);

                ValueEventListener vel = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            String userKey = ds.getKey();
                            DatabaseReference newdRef = fiery.getReference().child("Cart").child(cUser).child(userKey);
                            ValueEventListener vel2 = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot==null){
                                        Toast.makeText(getContext(),"NULL", Toast.LENGTH_LONG).show();
                                    }
                                    else if(snapshot.child("cpName").getValue().equals(cpName)){
                                        newdRef.setValue(changedCart);
                                        changes.setTitle("Updating To Cart");
                                        changes.setMessage("Please Wait while changes are saved");
                                        changes.setCanceledOnTouchOutside(false);
                                        changes.show();
                                        sendUserToCart();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            };
                            newdRef.addListenerForSingleValueEvent(vel2);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
               dFire.addListenerForSingleValueEvent(vel);
               trtex2.setText(stnewOrderTotal);
            }
        });

        ValueEventListener delVel = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String pDelKey = ds.getKey();
                    DatabaseReference pDelRef = fiery.getReference().child("Cart").child(cUser).child(pDelKey);
                    ValueEventListener delVel2 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot==null){
                                Toast.makeText(getContext(),"NULL", Toast.LENGTH_LONG).show();
                            }
                            else if(snapshot.child("quantity").getValue().equals("0")||snapshot.child("orderTotal").getValue().equals("0")){
                                pDelRef.setValue(null);
                                Toast.makeText(getContext(), "REMOVED ITEM FROM CART", Toast.LENGTH_LONG).show();
                                sendUserToCart();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    };
                    pDelRef.addListenerForSingleValueEvent(delVel2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dFire.addListenerForSingleValueEvent(delVel);





        return viewCartDetails;
    }
    public void onBackPressedCart()
    {
        AppCompatActivity cartBack = (AppCompatActivity) getContext();
        cartBack.getSupportFragmentManager().beginTransaction().replace(R.id.trolly, new trollyList()).addToBackStack(null).commit();
    }
    public void sendUserToCart(){
        changes.dismiss();
        AppCompatActivity cartProgress = (AppCompatActivity) getContext();
        cartProgress.getSupportFragmentManager().beginTransaction().replace(R.id.trolly, new trollyList()).addToBackStack(null).commit();
    }
}