package com.example.pizzaorderinh;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profDetails extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseDatabase fProf = FirebaseDatabase.getInstance();
    FirebaseAuth profAuth = FirebaseAuth.getInstance();
    String profUser = profAuth.getCurrentUser().getEmail();
    DatabaseReference dProf = fProf.getReference().child("Users");
    String mail,pehlaname,aakhriname, dial ,pata,janam;
    ProgressDialog loggingOut;
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
        View profView = inflater.inflate(R.layout.fragment_prof_details, container, false);
        TextView gmf = (TextView) profView.findViewById(R.id.gmailFirebase);
        TextView fname = (TextView) profView.findViewById(R.id.first_name);
        TextView lname = (TextView) profView.findViewById(R.id.last_name);
        TextView phf = (TextView) profView.findViewById(R.id.phoneFirebase);
        TextView dobf = (TextView) profView.findViewById(R.id.birthFirebase);
        TextView addf = (TextView) profView.findViewById(R.id.pataFirebase);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button logout = (Button) profView.findViewById(R.id.logout);
        Button edit = (Button) profView.findViewById(R.id.editProf);
        edit.setPaintFlags(edit.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        logout.setPaintFlags(logout.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        dProf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot users: snapshot.getChildren()){
                    String userEmail = users.child("email").getValue().toString();
                    if(userEmail.equals(profUser)){
                                    mail = users.child("email").getValue().toString();
                                    pehlaname = users.child("firstname").getValue().toString();
                                    aakhriname = users.child("lastname").getValue().toString();
                                    dial = users.child("contact").getValue().toString();
                                    pata = users.child("address").getValue().toString();
                                    janam = users.child("dob").getValue().toString();
                                    gmf.setText(mail);
                                    fname.setText(pehlaname);
                                    lname.setText(aakhriname);
                                    phf.setText(dial);
                                    addf.setText(pata);
                                    dobf.setText(janam);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToEditProf();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               baharJao();
            }
        });


        return profView;
    }

    private void sendUserToEditProf() {
        AppCompatActivity editProfJao = (AppCompatActivity) getContext();
        editProfJao.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        editProfJao.getSupportFragmentManager().beginTransaction().replace(R.id.start, new editProfile()).addToBackStack(null).commit();
    }

    private void baharJao() {
        loggingOut = new ProgressDialog(getContext());
        loggingOut.setTitle("Log Out");
        loggingOut.setMessage("Please Wait While You Are Being Logged Out");
        loggingOut.show();
        loggingOut.dismiss();
        AppCompatActivity bahar = (AppCompatActivity) getContext();
        bahar.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bahar.getSupportFragmentManager().beginTransaction().replace(R.id.start, new frag_login()).addToBackStack(null).commit();
        Toast.makeText(getContext(),"You have been Logged Out Succesfully",Toast.LENGTH_LONG).show();
    }
}