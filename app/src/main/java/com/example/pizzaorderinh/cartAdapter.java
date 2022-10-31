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

public class cartAdapter extends FirebaseRecyclerAdapter<cartItem,cartAdapter.cartHolder> {



    public cartAdapter(@NonNull FirebaseRecyclerOptions<cartItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull cartHolder holder, int position, @NonNull cartItem model) {
        holder.ct1.setText(model.getCpName());
        holder.ct2.setText(model.getOrderTotal());
        holder.ctQ.setText(model.getQuantity());
        Glide.with(holder.cimg.getContext()).load(model.getCpImage()).into(holder.cimg);
        holder.cartRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity actCart = (AppCompatActivity) view.getContext();
                actCart.getSupportFragmentManager().beginTransaction().replace(R.id.start, new trollyDetails(model.getQuantity(),model.getCpImage(),model.getCpName(), model.getOrderTotal())).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public cartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cartView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new cartHolder(cartView);
    }
    public class cartHolder extends RecyclerView.ViewHolder{
        ImageView cimg;
        TextView ct1,ct2,ctQ;
        RelativeLayout cartRel;
        public cartHolder(@NonNull View itemView) {
            super(itemView);
            cimg = (ImageView) itemView.findViewById(R.id.cartI);
            ct1 =(TextView) itemView.findViewById(R.id.cartN);
            ct2 =(TextView) itemView.findViewById(R.id.cartP);
            ctQ = (TextView) itemView.findViewById(R.id.carttextQuan);
            cartRel = (RelativeLayout) itemView.findViewById(R.id.cartLayout);
        }
    }
}
