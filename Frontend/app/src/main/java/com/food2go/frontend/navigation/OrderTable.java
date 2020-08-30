package com.food2go.frontend.navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.food2go.frontend.Enums.TableStatus;
import com.food2go.frontend.R;
import com.food2go.frontend.Services.Implementation.TableServices;
import com.food2go.frontend.interfaces.ITableListEventListener;
import com.food2go.frontend.models.ApiResponse;
import com.food2go.frontend.models.BaseResponse;
import com.food2go.frontend.models.OpenTableRequest;
import com.food2go.frontend.models.Restaurant;
import com.food2go.frontend.models.Table;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class OrderTable extends Fragment implements ITableListEventListener{

    private Context context = null;

    private ITableListEventListener mListener;

    private ArrayList<Table> ListTable;

    private Table selectedTable;
    private final String TABLE_NAME = "TableName";

    private RecyclerView view;
    private RecyclerView.Adapter adapter;

    private ImageView img;
    private ViewGroup rootLayout;
    private int[] _xDelta;
    private int[] _yDelta;
    List<LinearLayout> imageList = new ArrayList<>();
    Button add;
    Button submit;

    private NavController navController;
    public static final String APP_PREF_KEY = "app_pref";
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String RESTAURANT = "restaurant";
    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_table, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = getContext().getSharedPreferences(APP_PREF_KEY,MODE_PRIVATE);

        navController = Navigation.findNavController(view);

        Restaurant restaurant = new Restaurant();
        //restaurant.setCode(preferences.getInt(RESTAURANT,0));
        restaurant.setCode(preferences.getInt(RESTAURANT,0));

        ApiResponse<ArrayList<Table>> lstTables = TableServices.getAllTables(restaurant);
        lstTables.Subscribe(this::handleGetTablesSuccess, this::handleAPIFailure);
        context = view.getContext();

        _xDelta = new int[100];
        _yDelta = new int[100];
        rootLayout = (ViewGroup) view.findViewById(R.id.view_root);
        add = (Button) view.findViewById(R.id.createTable);
        submit = (Button) view.findViewById(R.id.submitChanges);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = this;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onTableStatusClick(Table table) {
        try{
            selectedTable = table;

            if(table.getTableStatus().equals(TableStatus.EMPTY.toString()))
            {
                // Display loading spinner before calling API
//                LoadingSpinnerHelper.displayLoadingSpinner(getActivity());

                ApiResponse<Boolean> isOpened = TableServices.openTable(new OpenTableRequest(table));
                isOpened.Subscribe(this::handleOpenTableSuccess, this::handleAPIFailure);
            }
            else
                goToTableDetail(table);

        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleGetTablesSuccess(BaseResponse<ArrayList<Table>> response){
        try{

            this.ListTable = response.GetData();

            // update Adapter
            for(Table table: this.ListTable){
                LinearLayout image;
                //ImageView img = image.findViewById(android.R.id.content);
                if(table.getTableStatus().equals("Serving")) {
                    image = (LinearLayout) getLayoutInflater().inflate(R.layout.single_table_occupied, null);
                }else{
                    image = (LinearLayout) getLayoutInflater().inflate(R.layout.single_table, null);
                }
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = table.getX();
                params.topMargin = table.getY();
                image.setLayoutParams(params);

                imageList.add(image);
                TextView textView = image.findViewById(R.id.tableNo);
                textView.setText("Table "+(imageList.indexOf(image)+1));
                image.setOnClickListener(new OrderTable.ChoiceTouchListener(imageList.indexOf(image),this));
                rootLayout.addView(image);
            }

        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private final class ChoiceTouchListener implements View.OnClickListener {
        int i;
        ITableListEventListener tablefragment;
        ChoiceTouchListener(int i, ITableListEventListener fragment){
            this.i = i;
            this.tablefragment = fragment;
        }

        @Override
        public void onClick(View v) {
            tablefragment.onTableStatusClick(ListTable.get(i));
        }

    }


    private void handleOpenTableSuccess(BaseResponse<Boolean> response){
        try{
            // Hide loading spinner once get API response
            //    LoadingSpinnerHelper.hideLoadingSpinner(vw);

            if(!response.IsSuccess())
            {
                Toast.makeText(context, response.GetMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            Boolean isOpened = response.GetData();

            if(isOpened){
                goToTableDetail(selectedTable);
            }

        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleAPIFailure(Throwable t){
        // Hide loading spinner once get API response
        //  LoadingSpinnerHelper.hideLoadingSpinner(vw);


        Toast.makeText(context, "Internal error happened. Please try later.",
                Toast.LENGTH_LONG).show();
    }

    private void goToTableDetail(Table table){

//        TableDetailFragment fragment = new TableDetailFragment(tableName);

//        getFragmentManager().beginTransaction()
//                .replace(R.id.main_activity_frame, fragment)
//                .commit();
        navController.navigate(R.id.action_orderTable_to_navigation_main_menu);
    }
}