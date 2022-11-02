package com.example.pizzaorderinh;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag_login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_login extends Fragment {


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    EditText em,pa;
    Button l,sig;
    ProgressDialog pd1;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FrameLayout flLogin;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public frag_login() {
        // Required empty public constructor
    }


    public static frag_login newInstance(String param1, String param2) {
        frag_login fragment = new frag_login();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View logView = inflater.inflate(R.layout.fragment_frag_login, container, false);
       flLogin = logView.findViewById(R.id.fragLogin);
        pd1 = new ProgressDialog(getContext());
        em = logView.findViewById(R.id.emailid);
        pa = logView.findViewById(R.id.password);
        l = logView.findViewById(R.id.log);
        sig = logView.findViewById(R.id.sign);
        l.setPaintFlags(l.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        sig.setPaintFlags(sig.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        sig.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AppCompatActivity signPeJao = (AppCompatActivity) getContext();
                signPeJao.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                signPeJao.getSupportFragmentManager().beginTransaction().replace(R.id.start, new frag_signup()).addToBackStack(null).commit();
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });

        return logView;
    }
    public void performLogin() {
        String e = em.getText().toString();
        String p = pa.getText().toString();

        if(!e.matches(emailpattern))
        {
            em.setError("Enter Correct Email");
        }
        else if(p.isEmpty()||p.length()<6)
        {
            pa.setError("Enter Proper Password");
        }
        else{
            pd1.setMessage("Please wait while Login...");
            pd1.setTitle("Login");
            pd1.setCanceledOnTouchOutside(false);
            pd1.show();

            mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        pd1.dismiss();
                        sendUsertonextactivity2();
                        Toast.makeText(getContext(),"Login Successful", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        pd1.dismiss();
                        Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUsertonextactivity2() {
        AppCompatActivity menuJao = (AppCompatActivity) getContext();
        menuJao.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        menuJao.getSupportFragmentManager().beginTransaction().replace(R.id.start, new homeScreen()).addToBackStack(null).commit();
    }
}