package com.food2go.frontend.mealmenu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.food2go.frontend.R;
import com.food2go.frontend.interfaces.IMenuItemsClickListener;
import com.food2go.frontend.models.Cart;
import com.food2go.frontend.models.Menu;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FoodDetailsFragment extends Fragment implements  View.OnClickListener{

    public FoodDetailsFragment() {
        // Required empty public constructor
    }

    SharedPreferences preferences;

    private NavController navController;

    ArrayList<Cart>  cartMenu = new ArrayList<>();
    int quantity=0;
    double total;
    double price;
    String itemName;
    int currentItemIndex;
    public static final String APP_PREF_KEY = "app_pref";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_details, container, false);


    }
    Button gotomenu;
    Button gotocart;
    Button addtoCart;
    Button  btn_plus;
    Button btn_minus;
    TextView tv_price;
    TextView tv_item;
    TextView tv_quantity;
    ImageView food_image;
    TextView tv_total;
    TextView success;


    int n = 0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
       tv_price= view.findViewById(R.id.tv_price);
       tv_item= view.findViewById(R.id.tv_item);
        food_image=view.findViewById(R.id.foodDetailImage);
       addtoCart= view.findViewById(R.id.addToCart);
        btn_plus=view.findViewById(R.id.btn_add);
        btn_minus = view.findViewById(R.id.btn_minus);
        tv_quantity = view.findViewById(R.id.tv_quantity);
        tv_total = view.findViewById(R.id.tv_total);

        gotocart = view.findViewById(R.id.go_to_cart);
        gotomenu = view.findViewById(R.id.go_to_menu);
        success = view.findViewById(R.id.success);



        btn_plus.setOnClickListener(this);
        btn_minus.setOnClickListener(this);
        addtoCart.setOnClickListener(this);
        gotomenu.setOnClickListener(this);
        gotocart.setOnClickListener(this);


        preferences = getActivity().getSharedPreferences(APP_PREF_KEY,MODE_PRIVATE);
        if(getArguments() !=null) {
            FoodDetailsFragmentArgs args = FoodDetailsFragmentArgs.fromBundle(getArguments());
            itemName = args.getItemName();
            price = Double.parseDouble(args.getPrice());
            tv_price.setText("Price "+args.getPrice());

            tv_item.setText(itemName);
            switch (itemName){
                case  "Tea":
                    food_image.setImageResource(R.drawable.tea);
                    break;
                case  "Ice-Tea":
                    food_image.setImageResource(R.drawable.icetea);
                    break;
                case  "Coke":
                    food_image.setImageResource(R.drawable.coke);
                    break;
                case  "Water":
                    food_image.setImageResource(R.drawable.waterbottle);
                    break;
                case  "Chicken Popcorn":
                    food_image.setImageResource(R.drawable.chickenpopcorn);
                    break;
                case  "Pan Fried Calamary":
                    food_image.setImageResource(R.drawable.calamary);
                    break;
                case  "Stuffed Mushroom":
                    food_image.setImageResource(R.drawable.mushroom);
                    break;
                case  "Ice-Cream":
                    food_image.setImageResource(R.drawable.icecream);
                    break;
                case  "Cone":
                    food_image.setImageResource(R.drawable.cone);
                    break;
                case  "Strawberry Ice-Cream":
                    food_image.setImageResource(R.drawable.cakeicecream);
                    break;
                case  "Sushi":
                    food_image.setImageResource(R.drawable.sushi);
                    break;
                case  "Burger":
                    food_image.setImageResource(R.drawable.burger);
                    break;
                case  "Manchurian":
                    food_image.setImageResource(R.drawable.manchurian);
                    break;
                case  "Salad":
                    food_image.setImageResource(R.drawable.salad);
                    break;
                case  "Choco-Bar":
                    food_image.setImageResource(R.drawable.chocobar);
                    break;
                default:
                    food_image.setImageResource(R.drawable.dine_in);

            }

            Cart cart[] = getAllSavedItems();
            if(cart != null) {
                int index = 0;
                for (Cart c : cart) {
                    System.out.println(c);
                    if(c.getItemName().equals(itemName)){
                        currentItemIndex = index;
                        quantity = c.getQuantity();
                        tv_quantity.setText(String.valueOf(quantity));
                    }
                    cartMenu.add(c);
                    index++;
                }
            }
            total = price * quantity;
            tv_total.setText("Total "+total);

        }




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                quantity++;
                success.setText("");
            break;
            case R.id.btn_minus:
                if(quantity>0) {
                    quantity--;
                }
                success.setText("");
                break;
            case R.id.addToCart:
                Cart cart;
                if(currentItemIndex>0) {
                     cart = cartMenu.get(currentItemIndex);
                    cart.setQuantity(quantity);
                }else {
                    cart = new Cart();
                    cart.setQuantity(quantity);
                    cart.setItemName(itemName);
                    cart.setItemPrice(price);
                    cartMenu.add(cart);
                }
                SharedPreferences preferences = getContext().getSharedPreferences(APP_PREF_KEY, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                Gson gson = new Gson();
                String jsonText = gson.toJson(cartMenu);
                editor.putString("cart", jsonText);
                editor.apply();
                success.setText("Successfully added to the cart");
                Toast.makeText(getContext(),"successfully saved to cart", Toast.LENGTH_SHORT);
            break;
            case R.id.go_to_cart:
                navController.navigate(R.id.action_foodDetailsFragment_to_cartFragment);
                break;
            case R.id.go_to_menu:
                navController.navigate(R.id.action_foodDetailsFragment_to_navigation_main_menu);
                break;
            default:
        }
        total = price * quantity;
        tv_total.setText("Total "+total);
        tv_quantity.setText(String.valueOf(quantity));
    }

    private Cart[] getAllSavedItems(){
        SharedPreferences preferences = getContext().getSharedPreferences(APP_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String jsonText = preferences.getString("cart", null);
        Cart[] cart = gson.fromJson(jsonText, Cart[].class);

        return cart;
    }
}