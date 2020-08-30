package com.food2go.frontend.mealmenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.food2go.frontend.R;
import com.food2go.frontend.adapter.CartRecyclerViewAdapter;
import com.food2go.frontend.adapter.RecyclerViewAdapter;
import com.food2go.frontend.interfaces.ICartUpdateListener;
import com.food2go.frontend.models.Cart;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CartFragment extends Fragment implements ICartUpdateListener {

    public CartFragment() {
        // Required empty public constructor
    }


    private NavController navController;
    TextView subtotal;
    TextView total;
    TextView tax;
    TextView button;
    CartRecyclerViewAdapter recyclerViewAdapter;

    RecyclerView.LayoutManager layoutManager;

    private RecyclerView recyclerView;

    ArrayList<Cart> cartMenu = new ArrayList<>();

    public static final String APP_PREF_KEY = "app_pref";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        subtotal = view.findViewById(R.id.subtotal);
        total = view.findViewById(R.id.total);
        tax = view.findViewById(R.id.tax);
        button = view.findViewById(R.id.confirm);
        Cart cart[] = getAllSavedItems();
        if(cart != null) {
            for (Cart c : cart) {
                cartMenu.add(c);
            }
        }

        if(cartMenu.size()>0){
            updateView();
            recyclerView = view.findViewById(R.id.cartRecycler);

             layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerViewAdapter= new CartRecyclerViewAdapter(cartMenu,this);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerView.setHasFixedSize(true);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_cartFragment_to_navigation_login);
            }
        });

    }

    void updateSavedCartItems(){
        SharedPreferences preferences = getContext().getSharedPreferences(APP_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String jsonText = gson.toJson(cartMenu);
        editor.putString("cart", jsonText);
        editor.apply();
    }

    void updateView(){
        double sub = 0;
        int count= 0;
        int itemIndex = -1;
        for(Cart cart: cartMenu){

            double individualTotal = cart.getItemPrice()*cart.getQuantity();
            sub = individualTotal + sub;
            if(cart.getQuantity()==0){
                itemIndex = count;
            }
            count++;
        }
        if(itemIndex >=0 && recyclerViewAdapter != null){
            cartMenu.remove(itemIndex);
            recyclerViewAdapter.notifyDataSetChanged();
            updateSavedCartItems();
        }

        double tx = sub*13/100;
        double ttl = sub + tx;

        total.setText(String.format("%.2f", ttl));
        subtotal.setText(String.format("%.2f", sub));
        tax.setText(String.format("%.2f", tx));

    }
    private Cart[] getAllSavedItems(){
        SharedPreferences preferences = getContext().getSharedPreferences(APP_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String jsonText = preferences.getString("cart", null);
        Cart[] cart = gson.fromJson(jsonText, Cart[].class);

        return cart;
    }

    @Override
    public void update() {
        updateView();
    }
}