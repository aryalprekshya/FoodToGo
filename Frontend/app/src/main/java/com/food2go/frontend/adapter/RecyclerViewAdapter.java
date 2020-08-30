package com.food2go.frontend.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.food2go.frontend.R;
import com.food2go.frontend.interfaces.IMenuItemsClickListener;
import com.food2go.frontend.models.Menu;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    ArrayList<Menu> menuList;
    IMenuItemsClickListener mainMenufragment;

    public RecyclerViewAdapter(IMenuItemsClickListener mainMenufragment, ArrayList<Menu> menuList) {
        this.menuList = menuList;
        this.mainMenufragment = mainMenufragment;
    }

   //Inflating the layout
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainmenu_view,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view );
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Menu menu = menuList.get(position);
        holder.imageView.setImageResource(menu.getImage());
        holder.textView.setText(menu.getName());
        holder.m.setPrice(menu.getPrice());
        holder.m.setCategory(menu.getCategory());

        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenufragment.onMenuItemsClick(menu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        Menu m;
        View mview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mview= itemView;
            imageView=itemView.findViewById(R.id.foodDetailImage);
            textView=itemView.findViewById(R.id.textView);
            m = new Menu();
        }


    }
}
