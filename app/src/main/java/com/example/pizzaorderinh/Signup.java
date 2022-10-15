package com.example.pizzaorderinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    EditText email,fName,lName,address,contact,dob,conp,password;
    Button su,lb;
    ProgressDialog pd;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
         mAuth = FirebaseAuth.getInstance();
         user = mAuth.getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference().child("Users");
        fName= findViewById(R.id.fname);
        lName=findViewById(R.id.lname);
        contact = findViewById(R.id.contact);
        email= findViewById(R.id.emid);
        address= findViewById(R.id.address);
        dob= findViewById(R.id.dob);
        password = findViewById(R.id.pass);
        conp = findViewById(R.id.conpass);
        su = findViewById(R.id.signup);
        lb = findViewById(R.id.logbutton);
        pd = new ProgressDialog(this);

        su.setPaintFlags(su.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        lb.setPaintFlags(lb.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logInKaro = new Intent(Signup.this,Login.class);
                startActivity(logInKaro);
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
                    userMap.put("Email", e);
                    userMap.put("First Name", fn);
                    userMap.put("Last Name", ln);
                    userMap.put("Contact", c);
                    userMap.put("Address", a);
                    userMap.put("DOB", d);
                    userMap.put("Password", p);
                    root.push().setValue(userMap);


            }
        });


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
                      Toast.makeText(Signup.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                  }
                  else{
                      pd.dismiss();
                      Toast.makeText(Signup.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                  }

                }
            });

        }
    }

    private void sendUsertonextactivity() {
        Intent intent = new Intent(Signup.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}