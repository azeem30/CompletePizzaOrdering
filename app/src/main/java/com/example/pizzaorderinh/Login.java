package com.example.pizzaorderinh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Login extends AppCompatActivity {

FirebaseAuth mAuth;
FirebaseUser user;
    EditText em,pa;
    Button l,sig;
    ProgressDialog pd1;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        pd1 = new ProgressDialog(this);
        em = findViewById(R.id.emailid);
        pa = findViewById(R.id.password);
        l = findViewById(R.id.log);
        sig = findViewById(R.id.sign);

        l.setPaintFlags(l.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        sig.setPaintFlags(sig.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);


        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signKaro = new Intent(Login.this,Signup.class);
                startActivity(signKaro);
            }
        });


        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });



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
                   Toast.makeText(Login.this,"Login Successful", Toast.LENGTH_SHORT).show();
               }
               else{
                   pd1.dismiss();
                   Toast.makeText(Login.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
               }
                }
            });
        }
    }

    private void sendUsertonextactivity2() {
        Intent in = new Intent(Login.this,Menu.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
    }

}