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

public class descFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String pgImage,  pgName, pgPrice;
    public descFragment() {
        // Required empty public constructor
    }
public descFragment(String pgImage, String pgName, String pgPrice){
this.pgImage=pgImage;
this.pgName=pgName;
this.pgPrice=pgPrice;
}

    public static descFragment newInstance(String param1, String param2) {
        descFragment fragment = new descFragment();
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
        View view =inflater.inflate(R.layout.fragment_desc, container, false);
        ImageView detI = view.findViewById(R.id.detImg);
        TextView detT1 = view.findViewById(R.id.detText1);
        TextView detT2 = view.findViewById(R.id.detText2);
        Button order = view.findViewById(R.id.addOrder);
        order.setPaintFlags(order.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        detT1.setText(pgName);
        detT2.setText(pgPrice);
        Glide.with(getContext()).load(pgImage).into(detI);

        return view;
    }
    public void onBackPressed()
    {
        AppCompatActivity act = (AppCompatActivity)getContext();
        act.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new recFragment()).addToBackStack(null).commit();
    }
}