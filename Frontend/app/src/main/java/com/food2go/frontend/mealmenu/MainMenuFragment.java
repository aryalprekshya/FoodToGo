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

public class MainMenuFragment extends Fragment implements IMenuItemsClickListener {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;


    private NavController navController;

    //Array to be displayed
    int []arr = {R.drawable.drink, R.drawable.appetizer, R.drawable.meal, R.drawable.dessert};


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
        title.setText(R.string.main_menu);
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager= new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter= new RecyclerViewAdapter(this,createDummyMenu());

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    private ArrayList<Menu> createDummyMenu() {
        Menu menu = new Menu("Category", "Drink" , R.drawable.drink,0);
        Menu menu1 = new Menu("Category", "Appetizer", R.drawable.appetizer,0);
        Menu menu2 = new Menu("Category", "Meal", R.drawable.meal,0);
        Menu menu3 = new Menu("Category", "Dessert", R.drawable.dessert,0);

        ArrayList<Menu> menuList = new ArrayList<Menu>();
        menuList.add(menu);
        menuList.add(menu1);
        menuList.add(menu2);
        menuList.add(menu3);

        return menuList;
    }

    @Override
    public void onMenuItemsClick(Menu menu) {
        switch(menu.getName()){
            case "Drink":
                navController.navigate(R.id.action_navigation_main_menu_to_navigation_drink_menu);
                break;
            case "Appetizer":
                navController.navigate(R.id.action_navigation_main_menu_to_navigation_appetizer_menu);
                break;
            case "Meal":
                navController.navigate(R.id.action_navigation_main_menu_to_navigation_meal_menu);
                break;
            case "Dessert":
                navController.navigate(R.id.action_navigation_main_menu_to_navigation_dessert_menu);
                break;
            default:

        }

    }
}