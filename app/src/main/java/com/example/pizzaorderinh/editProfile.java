package com.example.pizzaorderinh;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editProfile extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseDatabase fProfEdit = FirebaseDatabase.getInstance();
    FirebaseAuth profAuthEdit = FirebaseAuth.getInstance();
    String profUserEdit = profAuthEdit.getCurrentUser().getEmail();
    String profUserId = profAuthEdit.getCurrentUser().getUid();
    DatabaseReference dProfEdit = fProfEdit.getReference().child("Users");

    String editedFname,editedLname,editedCont,editedDob, editedAdd;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
  String pehlanameEdit,aakhrinameEdit,pataEdit,dialEdit,janamEdit,mailEdit,passEdit;
  HashMap <String,String> editPush;
    public editProfile() {
        // Required empty public constructor
    }

    public static editProfile newInstance(String param1, String param2) {
        editProfile fragment = new editProfile();
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
      View editProf =inflater.inflate(R.layout.fragment_edit_profile, container, false);
      @SuppressLint({"MissingInflatedId", "LocalSuppress"})
      TextView editMail = editProf.findViewById(R.id.gmailFirebaseEdit);
      EditText fnameEdit = editProf.findViewById(R.id.first_nameEdit);
        EditText lnameEdit = editProf.findViewById(R.id.last_nameEdit);
        EditText contEdit = editProf.findViewById(R.id.phoneFirebaseEdit);
        EditText addressEdit = editProf.findViewById(R.id.pataFirebaseEdit);
        EditText dobEdit = editProf.findViewById(R.id.birthFirebaseEdit);
        Button  logSaveChange = editProf.findViewById(R.id.saveProfChanges);
        TextView profEditLabel = editProf.findViewById(R.id.profViewEdit);
        profEditLabel.setPaintFlags(profEditLabel.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        logSaveChange.setPaintFlags(logSaveChange.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        dProfEdit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot users: snapshot.getChildren()){
                    String userEmail = users.child("email").getValue().toString();
                    if(userEmail.equals(profUserEdit)){
                        pehlanameEdit = users.child("firstname").getValue().toString();
                        aakhrinameEdit = users.child("lastname").getValue().toString();
                        dialEdit = users.child("contact").getValue().toString();
                        pataEdit = users.child("address").getValue().toString();
                        janamEdit = users.child("dob").getValue().toString();
                        mailEdit = users.child("email").getValue().toString();
                        passEdit = users.child("password").getValue().toString();
                        fnameEdit.setText(pehlanameEdit);
                        lnameEdit.setText(aakhrinameEdit);
                        contEdit.setText(dialEdit);
                        addressEdit.setText(pataEdit);
                        dobEdit.setText(janamEdit);
                        editMail.setText(mailEdit);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            logSaveChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  dProfEdit.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                          for(DataSnapshot edits: snapshot.getChildren()){
                              String existingName = edits.child("firstname").getValue().toString();
                              String existingUserId = edits.getKey();
                              if(existingName.equals(pehlanameEdit)) {
                                  editedFname = fnameEdit.getText().toString();
                                  editedLname = lnameEdit.getText().toString();
                                  editedCont = contEdit.getText().toString();
                                  editedAdd = addressEdit.getText().toString();
                                  editedDob = dobEdit.getText().toString();
                                  editPush = new HashMap<>();
                                  editPush.put("firstname",editedFname);
                                  editPush.put("lastname",editedLname);
                                  editPush.put("contact",editedCont);
                                  editPush.put("dob",editedDob);
                                  editPush.put("address",editedAdd);
                                  editPush.put("email",mailEdit);
                                  editPush.put("password",passEdit);
                                  dProfEdit.child(existingUserId).setValue(editPush);
                                  SendUserAgainToProfile();
                              }
                          }
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError error) {

                      }
                  });
                }
            });

      return editProf;
    }

    private void SendUserAgainToProfile() {
        AppCompatActivity vapasProfileJao = (AppCompatActivity)  getContext();
        vapasProfileJao.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        vapasProfileJao.getSupportFragmentManager().beginTransaction().replace(R.id.start, new profDetails()).addToBackStack(null).commit();
    }
}