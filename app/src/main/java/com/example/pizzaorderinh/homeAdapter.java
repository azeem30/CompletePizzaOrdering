package com.example.pizzaorderinh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class homeAdapter extends FirebaseRecyclerAdapter<menuItem,homeAdapter.trendHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public homeAdapter(@NonNull FirebaseRecyclerOptions<menuItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull trendHolder holder, int position, @NonNull menuItem model) {
        holder.hpT.setText(model.getPgName());
        holder.hpP.setText(model.getPgPrice());
        Glide.with(holder.hpI.getContext()).load(model.getPgImage()).into(holder.hpI);
        holder.hpCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity actMenu = (AppCompatActivity) view.getContext();
                actMenu.getSupportFragmentManager().beginTransaction().replace(R.id.start,new descFragment(model.getPgImage(),model.getPgName(),model.getPgPrice())).addToBackStack(null).commit();
            }
        });

    }

    @NonNull
    @Override
    public trendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View trending = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);
        return new trendHolder(trending);
    }

    public class trendHolder extends RecyclerView.ViewHolder{
          ImageView hpI;
          TextView hpT,hpP;
          CardView hpCV;
        public trendHolder(@NonNull View itemView) {
            super(itemView);
            hpI = itemView.findViewById(R.id.trendI);
            hpT = itemView.findViewById(R.id.trendT);
            hpP = itemView.findViewById(R.id.trendT2);
            hpCV = itemView.findViewById(R.id.hpCardView);
        }
    }
}
