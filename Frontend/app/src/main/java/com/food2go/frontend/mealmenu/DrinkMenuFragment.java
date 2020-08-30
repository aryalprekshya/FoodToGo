package com.food2go.frontend.mealmenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.food2go.frontend.R;
import com.food2go.frontend.adapter.RecyclerViewAdapter;
import com.food2go.frontend.interfaces.IMenuItemsClickListener;
import com.food2go.frontend.models.Menu;

import java.util.ArrayList;

public class DrinkMenuFragment extends Fragment implements IMenuItemsClickListener {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    private NavController navController;
    public DrinkMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        TextView title = view.findViewById(R.id.tv_main_menu);
        title.setText(R.string.drink_menu);
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager= new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter= new RecyclerViewAdapter(this,createDummyMenu());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);

        return view;
    }
    private ArrayList<Menu> createDummyMenu() {
        Menu menu = new Menu("Category", "Tea", R.drawable.tea,4.0);
        Menu menu1 = new Menu("Category", "Ice-Tea", R.drawable.icetea,3.0);
        Menu menu2 = new Menu("Category", "Coke", R.drawable.coke,2);
        Menu menu3 = new Menu("Category", "Water", R.drawable.waterbottle,2);

        ArrayList<Menu> menuList = new ArrayList<Menu>();
        menuList.add(menu);
        menuList.add(menu1);
        menuList.add(menu2);
        menuList.add(menu3);

        return menuList;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }
    @Override
    public void onMenuItemsClick(Menu menu) {
        DrinkMenuFragmentDirections.ActionNavigationDrinkMenuToFoodDetailsFragment action = DrinkMenuFragmentDirections.actionNavigationDrinkMenuToFoodDetailsFragment();
        action.setItemName(menu.getName());


        action.setPrice(String.valueOf(menu.getPrice()));
        navController.navigate(action);

    }
}