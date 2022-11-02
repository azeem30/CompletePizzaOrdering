package com.example.pizzaorderinh;

import static androidx.recyclerview.widget.LinearLayoutManager.*;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class homeScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseDatabase fbHome = FirebaseDatabase.getInstance();
    DatabaseReference dbHome = fbHome.getReference().child("menuPizzas");
    homeAdapter ha;
    public homeScreen() {
        // Required empty public constructor
    }

    public static homeScreen newInstance(String param1, String param2) {
        homeScreen fragment = new homeScreen();
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
        View homeView =inflater.inflate(R.layout.fragment_home_screen, container, false);
        RecyclerView hRec = (RecyclerView) homeView.findViewById(R.id.homeRec);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ScrollView hscr = (ScrollView) homeView.findViewById(R.id.homeScroll1);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ScrollView hscrall = homeView.findViewById(R.id.homeScrollAll);
        hscr.setSmoothScrollingEnabled(true);
hscrall.setSmoothScrollingEnabled(true);
@SuppressLint({"MissingInflatedId", "LocalSuppress"})
TextView home = homeView.findViewById(R.id.homeLabel);
        ImageView searchI = homeView.findViewById(R.id.homeSearchLogo);
        TextView searchT = homeView.findViewById(R.id.homeSearchLabel);
        ImageView menuI = homeView.findViewById(R.id.menuHomeImage);
        TextView menuT = homeView.findViewById(R.id.homeMenuLabel);
        ImageView profI = homeView.findViewById(R.id.homeProfImage);
        TextView profT = homeView.findViewById(R.id.homeProfLabel);
        ImageView cartI = homeView.findViewById(R.id.homeCartImage);
        TextView cartT = homeView.findViewById(R.id.homeCartLabel);
        ImageView orderI = homeView.findViewById(R.id.homeOrderImage);
        TextView orderT = homeView.findViewById(R.id.homeOrderLabel);

        home.setPaintFlags(home.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        searchI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSearch();
            }
        });
        searchT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSearch();
            }
        });

        menuI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMenu();
            }
        });
        menuT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMenu();
            }
        });

        profI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goProf();
            }
        });
        profT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goProf();
            }
        });

        cartI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goCart();
            }
        });
        cartT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goCart();
            }
        });

        orderI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goYourOrder();
            }
        });
        orderT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goYourOrder();
            }
        });

        LinearLayoutManager homeLayout = new LinearLayoutManager(getContext(),HORIZONTAL,false);
        hRec.setLayoutManager(homeLayout);
        FirebaseRecyclerOptions<menuItem> optionsH =
                new FirebaseRecyclerOptions.Builder<menuItem>()
                        .setQuery(dbHome.orderByChild("pgPrice"), menuItem.class)
                        .build();
        ha = new homeAdapter(optionsH);
        hRec.setAdapter(ha);
        return homeView;
    }

    private void goYourOrder() {
        AppCompatActivity gotoYourOrders = (AppCompatActivity) getContext();
        gotoYourOrders.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gotoYourOrders.getSupportFragmentManager().beginTransaction().replace(R.id.start,new yourOrders()).addToBackStack(null).commit();
    }

    private void goCart() {
        AppCompatActivity cartJao = (AppCompatActivity) getContext();
        cartJao.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        cartJao.getSupportFragmentManager().beginTransaction().replace(R.id.start,new trollyList()).addToBackStack(null).commit();
    }

    private void goProf() {
        AppCompatActivity profJao = (AppCompatActivity) getContext();
        profJao.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        profJao.getSupportFragmentManager().beginTransaction().replace(R.id.start,new profDetails()).addToBackStack(null).commit();
    }

    private void goMenu() {
        AppCompatActivity menuJao = (AppCompatActivity) getContext();
        menuJao.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        menuJao.getSupportFragmentManager().beginTransaction().replace(R.id.start, new recFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        ha.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        ha.stopListening();
    }
    private void goSearch(){
        AppCompatActivity searchJao = (AppCompatActivity) getContext();
        searchJao.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        searchJao.getSupportFragmentManager().beginTransaction().replace(R.id.start,new fragSearch()).addToBackStack(null).commit();
    }
}