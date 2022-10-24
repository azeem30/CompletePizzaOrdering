package com.example.pizzaorderinh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

public class fbAdapter extends FirebaseRecyclerAdapter<fbItem,fbAdapter.fbViewHolder>
{
    public fbAdapter(@NonNull FirebaseRecyclerOptions<fbItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull fbViewHolder holder, int position, @NonNull fbItem model) {
        holder.fTV.setText(model.getpName());
        Glide.with(holder.fIV.getContext()).load(model.getpImage()).into(holder.fIV);
holder.fIV.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AppCompatActivity act2 = (AppCompatActivity) view.getContext();
        act2.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new descFragment(model.getpImage(),model.getpName(), model.getpPrice())).addToBackStack(null).commit();
    }
});
    }

    @NonNull
    @Override
    public fbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View nazaara = LayoutInflater.from(parent.getContext()).inflate(R.layout.fbitem,parent,false);
      return new fbViewHolder(nazaara);
    }

    class fbViewHolder extends RecyclerView.ViewHolder
    {
        ImageView  fIV;
        TextView  fTV;

        public fbViewHolder(@NonNull View itemView) {
            super(itemView);
            fIV= (ImageView)itemView.findViewById(R.id.fImage);
            fTV=(TextView)itemView.findViewById(R.id.fText);
        }
    }
}
