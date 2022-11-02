package com.example.pizzaorderinh;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;


public class Thanks extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public Thanks() {
        // Required empty public constructor
    }

    public static Thanks newInstance(String param1, String param2) {
        Thanks fragment = new Thanks();
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
        // Inflate the layout for this fragment
        View thanksView =inflater.inflate(R.layout.fragment_thanks, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        FrameLayout thanksFrame = thanksView.findViewById(R.id.frameThanks);
        thanksFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity orderMoreFromMenu = (AppCompatActivity) getContext();
                orderMoreFromMenu.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                orderMoreFromMenu.getSupportFragmentManager().beginTransaction().replace(R.id.start,new recFragment()).addToBackStack(null).commit();
            }
        });
        return thanksView;
    }
}