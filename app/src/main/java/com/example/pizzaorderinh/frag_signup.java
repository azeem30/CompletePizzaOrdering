package com.example.pizzaorderinh;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class frag_signup extends Fragment {

    ProgressDialog pd;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String user= mAuth.getCurrentUser().getUid();

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("Users").child(user);
    EditText email,fName,lName,address,contact,dob,conp,password;
    Button su,lb;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public frag_signup() {
        // Required empty public constructor
    }

    public static frag_signup newInstance(String param1, String param2) {
        frag_signup fragment = new frag_signup();
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
        View signView = inflater.inflate(R.layout.fragment_frag_signup, container, false);

        fName= (EditText)signView.findViewById(R.id.fname);
        lName=(EditText)signView.findViewById(R.id.lname);
        contact = (EditText)signView.findViewById(R.id.contact);
        email= (EditText)signView.findViewById(R.id.emid);
        address= (EditText)signView.findViewById(R.id.address);
        dob= (EditText)signView.findViewById(R.id.dob);
        password = (EditText)signView.findViewById(R.id.pass);
        conp = (EditText)signView.findViewById(R.id.conpass);
        su = (Button) signView.findViewById(R.id.signup);
        lb = (Button) signView.findViewById(R.id.logbutton);
        pd = new ProgressDialog(getContext());

        su.setPaintFlags(su.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        lb.setPaintFlags(lb.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      sendUsertonextactivity();
            }
        });

        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e = email.getText().toString();
                String fn = fName.getText().toString();
                String ln = lName.getText().toString();
                String c = contact.getText().toString();
                String a = address.getText().toString();
                String d = dob.getText().toString();
                String p = password.getText().toString();
                performAuth();
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("email", e);
                userMap.put("firstname", fn);
                userMap.put("lastname", ln);
                userMap.put("contact", c);
                userMap.put("address", a);
                userMap.put("dob", d);
                userMap.put("password", p);
                root.setValue(userMap);
            }
        });
        return signView;
    }
    public void performAuth() {

        String e = email.getText().toString();
        String p = password.getText().toString();
        String c = contact.getText().toString();
        String confirm = conp.getText().toString();
        if(!e.matches(emailpattern))
        {
            email.setError("Enter Correct Email");
        }
        else if(p.isEmpty()||p.length()<6)
        {
            password.setError("Enter Proper Password");
        }
        else if(c.isEmpty()||c.length()>10)
        {
            contact.setError("Enter proper Contact");
        }
        else if(!p.equals(confirm))
        {
            conp.setError("Password not matched");
        }
        else{
            pd.setMessage("Please wait while Registration ...");
            pd.setTitle("Registration");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            mAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        pd.dismiss();
                        sendUsertonextactivity();
                        Toast.makeText(getContext(),"Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        pd.dismiss();
                        Toast.makeText(getContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }

    private void sendUsertonextactivity() {
        AppCompatActivity loginJao = (AppCompatActivity) getContext();
        loginJao.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginJao.getSupportFragmentManager().beginTransaction().replace(R.id.start, new frag_login()).addToBackStack(null).commit();
    }
}