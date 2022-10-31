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
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link confirmOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class confirmOrder extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseDatabase fConfirm = FirebaseDatabase.getInstance();
    FirebaseAuth orderAuth = FirebaseAuth.getInstance();
    String orderID = orderAuth.getCurrentUser().getUid();
    String orderMail = orderAuth.getCurrentUser().getEmail();
    DatabaseReference dUsers = fConfirm.getReference().child("Users");
    DatabaseReference dCart = fConfirm.getReference().child("Cart").child(orderID);
    DatabaseReference dConfirmOrder = fConfirm.getReference().child("Orders").child(orderID);
    HashMap<String, String> confirmOrderMap;
    List costList;
    int pop,now,add=0,totalConfirmCost;
    String totalConfirmCostString;
    private String mParam1;
    private String mParam2;

    public confirmOrder() {
        // Required empty public constructor
    }


    public static confirmOrder newInstance(String param1, String param2) {
        confirmOrder fragment = new confirmOrder();
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
        View confOrdView = inflater.inflate(R.layout.fragment_confirm_order, container, false);
        TextView delLabel = confOrdView.findViewById(R.id.confirmAddressView);
        TextView orderAddress = confOrdView.findViewById(R.id.confirmAddress);
        TextView payLabel = confOrdView.findViewById(R.id.confirmAddressPaymentView);
        TextView cashLabel = confOrdView.findViewById(R.id.cashView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView costLabel = confOrdView.findViewById(R.id.confirmOrderTotalCostView);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView confirmOrderTotal = confOrdView.findViewById(R.id.confirmOrderTotalCost);
        CheckBox cash = confOrdView.findViewById(R.id.confirmCash);
        delLabel.setPaintFlags(delLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        costLabel.setPaintFlags(costLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        payLabel.setPaintFlags(payLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        cashLabel.setPaintFlags(cashLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Button confirmedOrders = confOrdView.findViewById(R.id.confirmOrder);
        confirmedOrders.setPaintFlags(confirmedOrders.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        dUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot users : snapshot.getChildren()) {
                    String userEmail = users.child("email").getValue().toString();
                    String userOrderKey = users.getKey();
                    if (userEmail.equals(orderMail)) {
                        String retrievedPata = users.child("address").getValue().toString();
                        orderAddress.setText(retrievedPata);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                costList = new ArrayList<Integer>();
                for (DataSnapshot orderItems : snapshot.getChildren()) {
                    String uniqueOrderKey = orderItems.getKey();
                    String perOrderPrice = orderItems.child("orderTotal").getValue().toString();
                    pop = Integer.parseInt(perOrderPrice);
                    costList.add(pop);
                }
                for (int i = 0; i < costList.size(); i++) {
                    now = (int) costList.get(i);
                    add = add + now;
                }
                totalConfirmCost = add;
                totalConfirmCostString = String.valueOf(totalConfirmCost);
                confirmOrderTotal.setText(totalConfirmCostString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toast.makeText(getContext(), "Please Select A Payment Method", Toast.LENGTH_LONG).show();
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cash.setChecked(true);
            }
        });

            confirmedOrders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                    dCart.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot orders : snapshot.getChildren()) {
                                confirmOrderMap = new HashMap<>();
                                String oImg = orders.child("cpImage").getValue().toString();
                                String oName = orders.child("cpName").getValue().toString();
                                String oQuan = orders.child("quantity").getValue().toString();
                                String perP = orders.child("orderTotal").getValue().toString();
                                confirmOrderMap.put("orderI", oImg);
                                confirmOrderMap.put("orderN", oName);
                                confirmOrderMap.put("orderP", perP);
                                confirmOrderMap.put("orderQ", oQuan);
                                confirmOrderMap.put("orderTotalCost", totalConfirmCostString);
                                dConfirmOrder.push().setValue(confirmOrderMap);
                                dCart.setValue(null);
                                sendUserToMenu();
                                sendUserToTrolly();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

        return confOrdView;
    }

    private void sendUserToTrolly() {
        AppCompatActivity goTrolly = (AppCompatActivity) getContext();
        goTrolly.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        goTrolly.getSupportFragmentManager().beginTransaction().replace(R.id.start, new trollyList()).addToBackStack(null).commit();
    }

    private void sendUserToMenu() {
        AppCompatActivity goMenu = (AppCompatActivity) getContext();
        goMenu.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        goMenu.getSupportFragmentManager().beginTransaction().replace(R.id.start, new recFragment()).addToBackStack(null).commit();
    }
}