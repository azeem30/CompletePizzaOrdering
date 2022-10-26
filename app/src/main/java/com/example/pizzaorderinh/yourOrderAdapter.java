package com.example.pizzaorderinh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class yourOrderAdapter extends FirebaseRecyclerAdapter<orderItem,yourOrderAdapter.orderViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public yourOrderAdapter(@NonNull FirebaseRecyclerOptions<orderItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull orderViewHolder holder, int position, @NonNull orderItem model) {
        holder.nameOrder.setText(model.getOrderN());
        holder.priceOrder.setText(model.getOrderP());
        holder.quantOrder.setText(model.getOrderQ());
        Glide.with(holder.imgOrder.getContext()).load(model.getOrderI()).into(holder.imgOrder);
    }

    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ordView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new orderViewHolder(ordView);
    }

    public class orderViewHolder extends RecyclerView.ViewHolder{
        ImageView imgOrder;
        TextView nameOrder, quantOrder, priceOrder , totalOrderCost;
        public orderViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOrder = itemView.findViewById(R.id.oImage);
            nameOrder = itemView.findViewById(R.id.oName);
            quantOrder= itemView.findViewById(R.id.orderQuan);
            priceOrder = itemView.findViewById(R.id.oPrice);
        }
    }
}
