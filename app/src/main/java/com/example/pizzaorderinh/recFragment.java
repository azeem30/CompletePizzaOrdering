package com.example.pizzaorderinh;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.A;

import java.util.Objects;


public class recFragment extends Fragment{

FirebaseDatabase fb = FirebaseDatabase.getInstance();
DatabaseReference db = fb.getReference().child("menuPizzas");


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView mRec;
    menuAdapter ma;
    public recFragment() {
        // Required empty public constructor
    }



    public static recFragment newInstance(String param1, String param2) {
        recFragment fragment = new recFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rec, container, false);
        mRec=(RecyclerView) view.findViewById(R.id.menuRec);
        GridLayoutManager grm = new GridLayoutManager(getContext(),2);
        mRec.setLayoutManager(grm);

        FirebaseRecyclerOptions<menuItem> options =
                new FirebaseRecyclerOptions.Builder<menuItem>()
                        .setQuery(db, menuItem.class)
                        .build();
        ma = new menuAdapter(options);
        mRec.setAdapter(ma);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        ma.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        ma.stopListening();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.sortmenu, menu);
        inflater.inflate(R.menu.searchmenu,menu);
        inflater.inflate(R.menu.menu_cart,menu);
        inflater.inflate(R.menu.account,menu);
        inflater.inflate(R.menu.your_orders,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.menu_orders){
            AppCompatActivity gotoYourOrders = (AppCompatActivity) getContext();
            gotoYourOrders.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            gotoYourOrders.getSupportFragmentManager().beginTransaction().replace(R.id.start,new yourOrders()).addToBackStack(null).commit();
        }
        if(id==R.id.acc){
            AppCompatActivity profJao = (AppCompatActivity) getContext();
            profJao.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            profJao.getSupportFragmentManager().beginTransaction().replace(R.id.start,new profDetails()).addToBackStack(null).commit();
        }
        if(id==R.id.cartMenu){
            AppCompatActivity cartJao = (AppCompatActivity) getContext();
            cartJao.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            cartJao.getSupportFragmentManager().beginTransaction().replace(R.id.start,new trollyList()).addToBackStack(null).commit();

        }
        if(id==R.id.search){
          AppCompatActivity searchJao = (AppCompatActivity) getContext();
            searchJao.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            searchJao.getSupportFragmentManager().beginTransaction().replace(R.id.start,new fragSearch()).addToBackStack(null).commit();

        }
        if(id==R.id.action_sort){
            showSortDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortDialog() {
        String[] sortOpt = {"Default","Price: Low To High","Price: High to Low","Alphabetical Order"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sort by").setIcon(R.drawable.ic_action_sort).setItems(sortOpt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    processSortdef();
                }
                if(i==1)
                {
                    processSortprice();
                }
                if(i==2)
                {
                    processSortrevprice();
                }
                if(i==3)
                {
                    processSortalpha();
                }
            }
        });
        builder.show();
    }

    private void processSortrevprice() {
        GridLayoutManager gRev = new GridLayoutManager(getContext(),2);
        gRev.setReverseLayout(true);
        FirebaseRecyclerOptions<menuItem> options4 =
                new FirebaseRecyclerOptions.Builder<menuItem>()
                        .setQuery(db.orderByChild("pgPrice"), menuItem.class)
                        .build();
        ma = new menuAdapter(options4);
        ma.startListening();
        mRec.setAdapter(ma);
        mRec.setLayoutManager(gRev);
    }

    private void processSortdef() {
        FirebaseRecyclerOptions<menuItem> options1 =
                new FirebaseRecyclerOptions.Builder<menuItem>()
                        .setQuery(db, menuItem.class)
                        .build();
        ma = new menuAdapter(options1);
        ma.startListening();
        mRec.setAdapter(ma);
    }

    private void processSortalpha() {
        FirebaseRecyclerOptions<menuItem> options3 =
                new FirebaseRecyclerOptions.Builder<menuItem>()
                        .setQuery(db.orderByChild("pgName"), menuItem.class)
                        .build();
        ma = new menuAdapter(options3);
        ma.startListening();
        mRec.setAdapter(ma);
    }

    private void processSortprice() {
        FirebaseRecyclerOptions<menuItem> options2 =
                new FirebaseRecyclerOptions.Builder<menuItem>()
                        .setQuery(db.orderByChild("pgPrice"), menuItem.class)
                        .build();
        ma = new menuAdapter(options2);
        ma.startListening();
        mRec.setAdapter(ma);
    }
}