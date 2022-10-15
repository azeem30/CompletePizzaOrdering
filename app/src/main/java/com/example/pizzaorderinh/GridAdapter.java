package com.example.pizzaorderinh;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

public class GridAdapter extends BaseAdapter {

    Context conte;
            private final String [] pizzaName;
    private final int [] pImage;

    LayoutInflater inf;

    public GridAdapter(Context context, String[] pizzaName, int[] pImage) {
        this.conte = context;
        this.pizzaName = pizzaName;
        this.pImage = pImage;
    }

    @Override
    public int getCount() {
        return pizzaName.length ;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {


          inf = (LayoutInflater) conte.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          view = inf.inflate(R.layout.grid_item,null);



        ImageView iGv = view.findViewById(R.id.gImage);
        TextView tGv = view.findViewById(R.id.gText);
        iGv.setImageResource(pImage[position]);
        tGv.setText(pizzaName[position]);

        return view;
    }

}
