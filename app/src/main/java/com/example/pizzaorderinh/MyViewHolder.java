package com.example.pizzaorderinh;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView iv;
    TextView name;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        iv= itemView.findViewById(R.id.image1);
        name = itemView.findViewById(R.id.textview1);

    }
}
