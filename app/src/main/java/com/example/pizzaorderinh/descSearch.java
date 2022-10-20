package com.example.pizzaorderinh;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class descSearch extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
String pImage,pName,pPrice;

    private String mParam1;
    private String mParam2;

    public descSearch() {
        // Required empty public constructor
    }

public descSearch(String pImage, String pName, String pPrice){
        this.pImage=pImage;
        this.pName=pName;
        this.pPrice = pPrice;
}
    public static descSearch newInstance(String param1, String param2) {
        descSearch fragment = new descSearch();
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
        View view = inflater.inflate(R.layout.fragment_desc_search, container, false);
        ImageView seaI = view.findViewById(R.id.seaImg);
        TextView seaT1 = view.findViewById(R.id.seaText1);
        TextView seaT2 = view.findViewById(R.id.seaText2);
        Button seaorder = view.findViewById(R.id.addOrderS);
        seaorder.setPaintFlags(seaorder.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        seaT1.setText(pName);
        seaT2.setText(pPrice);
        Glide.with(getContext()).load(pImage).into(seaI);
        return view;
    }
    public void onBackPressed()
    {
        AppCompatActivity act = (AppCompatActivity)getContext();
        act.getSupportFragmentManager().beginTransaction().replace(R.id.swapper,new fragSearch()).addToBackStack(null).commit();
    }
}