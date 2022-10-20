package com.example.pizzaorderinh;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class fragSearch extends Fragment {

    RecyclerView sRec;
    fbAdapter fba;
    FirebaseDatabase fb = FirebaseDatabase.getInstance();
    DatabaseReference db = fb.getReference().child("Pizzas");
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public fragSearch() {
        // Required empty public constructor
    }



    public static fragSearch newInstance(String param1, String param2) {
        fragSearch fragment = new fragSearch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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
        View view = inflater.inflate(R.layout.fragment_frag_search, container, false);
        sRec = view.findViewById(R.id.seaR);
        sRec.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<fbItem> optionsS =
                new FirebaseRecyclerOptions.Builder<fbItem>()
                        .setQuery(db, fbItem.class)
                        .build();
        fba = new fbAdapter(optionsS);
        sRec.setAdapter(fba);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        fba.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        fba.stopListening();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.searchmenu,menu);
        MenuItem mi = menu.findItem(R.id.search);
        SearchView sv =(SearchView)mi.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void processSearch(String s) {
        FirebaseRecyclerOptions<fbItem> optionsN =
                new FirebaseRecyclerOptions.Builder<fbItem>()
                        .setQuery(db.orderByChild("pName").startAt(s.toUpperCase()).endAt(s.toLowerCase()+"\uf8ff"), fbItem.class)
                        .build();

        fba = new fbAdapter(optionsN);
        fba.startListening();
        sRec.setAdapter(fba);
    }
}