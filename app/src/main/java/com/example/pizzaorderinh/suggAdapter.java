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

public class suggAdapter extends FirebaseRecyclerAdapter<menuItem,suggAdapter.suggMenuHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public suggAdapter(@NonNull FirebaseRecyclerOptions<menuItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull suggMenuHolder holder, int position, @NonNull menuItem model) {
                   holder.suggestionN.setText(model.getPgName());
                   holder.suggestionP.setText(model.getPgPrice());
                   Glide.with(holder.suggestionI.getContext()).load(model.getPgImage()).into(holder.suggestionI);
                   holder.suggestionN.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           AppCompatActivity actSugg = (AppCompatActivity) view.getContext();
                           actSugg.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new descFragment(model.getPgImage(),model.getPgName(),model.getPgPrice())).addToBackStack(null).commit();
                       }
                   });
    }

    @NonNull
    @Override
    public suggAdapter.suggMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View suggView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sugg_item,parent,false);
        return new suggMenuHolder(suggView);
    }

   public class suggMenuHolder extends RecyclerView.ViewHolder{
         ImageView suggestionI;
         TextView suggestionN, suggestionP;
        public suggMenuHolder(@NonNull View itemView) {
            super(itemView);
            suggestionI = itemView.findViewById(R.id.suggI);
            suggestionN= itemView.findViewById(R.id.suggN);
            suggestionP = itemView.findViewById(R.id.suggP);
        }
    }
}
