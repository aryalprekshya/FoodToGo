package com.food2go.frontend.navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.food2go.frontend.Enums.TableStatus;
import com.food2go.frontend.R;
import com.food2go.frontend.Services.Implementation.TableServices;
import com.food2go.frontend.Utilities.LoadingSpinnerHelper;
import com.food2go.frontend.interfaces.ITableListEventListener;
import com.food2go.frontend.models.ApiResponse;
import com.food2go.frontend.models.BaseResponse;
import com.food2go.frontend.models.OpenTableRequest;
import com.food2go.frontend.models.Restaurant;
import com.food2go.frontend.models.Table;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class TableFragment extends Fragment implements ITableListEventListener {

    private Context context = null;

    private NavController navController;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
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

    //View vw;

    public static final String APP_PREF_KEY = "app_pref";
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String RESTAURANT = "restaurant";
    SharedPreferences preferences;
    public TableFragment() {    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        preferences = getContext().getSharedPreferences(APP_PREF_KEY,MODE_PRIVATE);

        Restaurant restaurant = new Restaurant();
        restaurant.setCode(preferences.getInt(RESTAURANT,0));
        ApiResponse<ArrayList<Table>> lstTables = TableServices.getAllTables(restaurant);
        lstTables.Subscribe(this::handleGetTablesSuccess, this::handleAPIFailure);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //view = (RecyclerView) inflater.inflate(R.layout.fragment_table_list, container, false);

        // Set the adapter
//        if (view instanceof RecyclerView) {
//            context = view.getContext();
//            if (mColumnCount <= 1) {
//                view.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                view.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//        }

        View view =  inflater.inflate(R.layout.fragment_add_table, container, false);


        //LoadingSpinnerHelper.displayLoadingSpinner(view);

        navController = Navigation.findNavController(view);


        _xDelta = new int[100];
        _yDelta = new int[100];
        rootLayout = (ViewGroup) view.findViewById(R.id.view_root);
        add = (Button) view.findViewById(R.id.createTable);
        submit = (Button) view.findViewById(R.id.submitChanges);

//        add.setOnClickListener(this);
//        submit.setOnClickListener(this);

//
//        LoadingSpinnerHelper.displayLoadingSpinner(getActivity());
//
//        ApiResponse<ArrayList<Table>> lstTables = TableServices.getAllTables();
//        lstTables.Subscribe(this::handleGetTablesSuccess, this::handleAPIFailure);


        return view;
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
               //
                // LoadingSpinnerHelper.displayLoadingSpinner(vw);

                ApiResponse<Boolean> isOpened = TableServices.openTable(new OpenTableRequest(table));
                isOpened.Subscribe(this::handleOpenTableSuccess, this::handleAPIFailure);
            }
            else
                goToTableDetail(selectedTable);

        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleGetTablesSuccess(BaseResponse<ArrayList<Table>> response){
        try{
//            // Hide loading spinner once get API response
//            LoadingSpinnerHelper.hideLoadingSpinner(getActivity());
//
//            if(!response.IsSuccess())
//            {
//                Toast.makeText(context, response.GetMessage(), Toast.LENGTH_LONG).show();
//                return;
//            }
//
//            this.ListTable = response.GetData();
//
//            // update Adapter
//            adapter = new MyTableRecyclerViewAdapter(context , this.ListTable, mListener);
//
//
//            view.setAdapter(adapter);

            // Hide loading spinner once get API response
          //  LoadingSpinnerHelper.hideLoadingSpinner(vw);


            this.ListTable = response.GetData();

            // update Adapter
            for(Table table: this.ListTable){
                LinearLayout image;
                //ImageView img = image.findViewById(android.R.id.content);
                if(table.getTableStatus().equals("Serving")) {
                     image = (LinearLayout) getLayoutInflater().inflate(R.layout.single_table_occupied, null);

                    //img.setImageDrawable(getResources().getDrawable(R.drawable.chair_occupied));
                }else{
                    image = (LinearLayout) getLayoutInflater().inflate(R.layout.single_table, null);

                    //img.setImageDrawable(getResources().getDrawable(R.drawable.chair_empty));
                }
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = table.getX();
                params.topMargin = table.getY();
                image.setLayoutParams(params);

                imageList.add(image);
                TextView textView = image.findViewById(R.id.tableNo);
                textView.setText("Table "+(imageList.indexOf(image)+1));
                image.setOnClickListener(new TableFragment.ChoiceTouchListener(imageList.indexOf(image),this));
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

    private void goToTableDetail(Table tableName){
//        TableDetailFragment fragment = new TableDetailFragment(tableName);

//        getFragmentManager().beginTransaction()
//                .replace(R.id.main_activity_frame, fragment)
//                .commit();
//        navController.navigate(R.id.action_tableFragment_to_navigation_main_menu);
    }
}
