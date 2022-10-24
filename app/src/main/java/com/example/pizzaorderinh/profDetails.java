package com.example.pizzaorderinh;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class profDetails extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseDatabase fProf= FirebaseDatabase.getInstance();
    String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference dProf = fProf.getReference().child("Users").child(uID);
    String uMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    String mail,pehlaname, aakhriname, number, janam, pata;
    private String mParam1;
    private String mParam2;

    public profDetails() {
        // Required empty public constructor
    }


    public static profDetails newInstance(String param1, String param2) {
        profDetails fragment = new profDetails();
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
        View profView =inflater.inflate(R.layout.fragment_prof_details, container, false);
        TextView gmf = profView.findViewById(R.id.gmailFirebase);
        TextView fname = profView.findViewById(R.id.first_name);
        TextView lname= profView.findViewById(R.id.last_name);
        TextView phf = profView.findViewById(R.id.phoneFirebase);
        TextView dobf = profView.findViewById(R.id.birthFirebase);
        TextView addf = profView.findViewById(R.id.pataFirebase);

        return profView;
    }
}