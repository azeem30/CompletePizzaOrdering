package com.example.pizzaorderinh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

public class fbAdapter extends FirebaseRecyclerAdapter<menuItem,fbAdapter.fbViewHolder>
{
    public fbAdapter(@NonNull FirebaseRecyclerOptions<menuItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull fbViewHolder holder, int position, @NonNull menuItem model) {
        holder.fTV.setText(model.getPgName());
        holder.fPV.setText(model.getPgPrice());
        Glide.with(holder.fIV.getContext()).load(model.getPgImage()).into(holder.fIV);
        holder.sRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity act2 = (AppCompatActivity) view.getContext();
                act2.getSupportFragmentManager().beginTransaction().replace(R.id.start,new descFragment(model.getPgImage(),model.getPgName(), model.getPgPrice())).addToBackStack(null).commit();
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
        TextView  fTV,fPV;
        CardView sRel;

        public fbViewHolder(@NonNull View itemView) {
            super(itemView);
            fIV= (ImageView)itemView.findViewById(R.id.fImage);
            fTV=(TextView)itemView.findViewById(R.id.fText);
            fPV=(TextView) itemView.findViewById(R.id.fPrice);
            sRel = (CardView) itemView.findViewById(R.id.sCard);
        }
    }
}
