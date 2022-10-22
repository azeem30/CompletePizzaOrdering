package com.example.pizzaorderinh;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class trollyDetails extends Fragment {

    String quantity,cpImage,cpName,orderTotal;
    ImageView trimg1;
    TextView trtex1,trtex2,trtex3;
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

        trtex1.setText(cpName);
        trtex2.setText(orderTotal);
        trtex3.setText(quantity);
        Glide.with(getContext()).load(cpImage).into(trimg1);
        return viewCartDetails;
    }
    public void onBackPressedCart()
    {
        AppCompatActivity cartBack = (AppCompatActivity) getContext();
        cartBack.getSupportFragmentManager().beginTransaction().replace(R.id.trolly, new trollyList()).addToBackStack(null).commit();
    }
}