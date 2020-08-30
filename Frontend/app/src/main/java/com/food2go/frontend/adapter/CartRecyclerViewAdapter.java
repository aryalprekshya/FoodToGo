package com.food2go.frontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.food2go.frontend.R;
import com.food2go.frontend.interfaces.ICartUpdateListener;
import com.food2go.frontend.models.Cart;
import com.food2go.frontend.models.Menu;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.MyViewHolder> {

    ArrayList<Cart> cartList;
    ICartUpdateListener updateListener;

    public CartRecyclerViewAdapter(ArrayList<Cart> carts, ICartUpdateListener updateListener) {
        this.cartList = carts;
        this.updateListener = updateListener;
    }

    //Inflating the layout
    @NonNull
    @Override
    public CartRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        CartRecyclerViewAdapter.MyViewHolder myViewHolder = new CartRecyclerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewAdapter.MyViewHolder holder, int position) {
        Cart c = cartList.get(position);
        holder.tv_foodItem.setText(c.getItemName());
        holder.quantity.setText(String.valueOf(c.getQuantity()));
        holder.price.setText(String.valueOf(c.getItemPrice()*c.getQuantity()));

        holder.plus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = c.getQuantity();
                q++;
                c.setQuantity(q);
                holder.quantity.setText(String.valueOf(q));
                double amount = q * c.getItemPrice();
                holder.price.setText(String.valueOf(amount));
                updateListener.update();
            }
        });

        holder.minus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int q = c.getQuantity();
                q--;
                if(q<0) return;
                c.setQuantity(q);
                holder.quantity.setText(String.valueOf(q));
                double amount = q * c.getItemPrice();
                holder.price.setText(String.format("%.2f", amount));
                updateListener.update();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView tv_foodItem;
        TextView quantity;
        Button minus_button;
        Button plus_button;
        TextView price;
        Menu m;
        View mview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mview= itemView;
            quantity=itemView.findViewById(R.id.quantity);
            tv_foodItem=itemView.findViewById(R.id.tv_foodItem);
            minus_button=itemView.findViewById(R.id.minus_button);
            plus_button=itemView.findViewById(R.id.plus_button);
            price=itemView.findViewById(R.id.tv_price);
            m = new Menu();

        }


    }
}
