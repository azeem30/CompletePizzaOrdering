package com.example.pizzaorderinh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class menuAdapter extends FirebaseRecyclerAdapter<menuItem,menuAdapter.menuHolder> {

    public menuAdapter(@NonNull FirebaseRecyclerOptions<menuItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull menuHolder holder, int position, @NonNull menuItem model) {
        holder.mt1.setText(model.getPgName());
        holder.mt2.setText(model.getPgPrice());
        Glide.with(holder.mimg.getContext()).load(model.getPgImage()).into(holder.mimg);

        holder.mRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity act = (AppCompatActivity) view.getContext();
                act.getSupportFragmentManager().beginTransaction().replace(R.id.start,new descFragment(model.getPgImage(),model.getPgName(),model.getPgPrice())).addToBackStack(null).commit();

            }
        });
    }

    @NonNull
    @Override
    public menuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
       return new menuHolder(view);
    }

    public class menuHolder extends RecyclerView.ViewHolder{
        ImageView mimg;
        TextView mt1;
        TextView mt2;
        RelativeLayout mRel;
        public menuHolder(@NonNull View itemView) {
            super(itemView);
            mimg = (ImageView) itemView.findViewById(R.id.menuI);
            mt1 =(TextView) itemView.findViewById(R.id.menuT1);
            mt2 =(TextView) itemView.findViewById(R.id.menuT2);
            mRel = (RelativeLayout) itemView.findViewById(R.id.menuRel);
        }
    }
}
