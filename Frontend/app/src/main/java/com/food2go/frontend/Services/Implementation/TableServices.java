package com.food2go.frontend.Services.Implementation;



import com.food2go.frontend.Services.Interface.ITableServices;
import com.food2go.frontend.Utilities.HttpHelper;
import com.food2go.frontend.models.AddNewTablesRequest;
import com.food2go.frontend.models.AddOrderToTableRequest;
import com.food2go.frontend.models.ApiResponse;
import com.food2go.frontend.models.ChangeStatusOfOrderRequest;
import com.food2go.frontend.models.GetOrderedFoodRequest;
import com.food2go.frontend.models.OpenTableRequest;
import com.food2go.frontend.models.Order;
import com.food2go.frontend.models.Restaurant;
import com.food2go.frontend.models.Table;
import com.food2go.frontend.models.TableTransactionDetail;

import java.util.ArrayList;

public class TableServices {
    public static ApiResponse<ArrayList<Table>> getAllTables(Restaurant req) {
        try{
            ITableServices tableServices = HttpHelper.CreateApiService(ITableServices.class);

            return new ApiResponse(tableServices.getAllTables(req));

        }catch(Exception ex){
            throw ex;
        }
    }

    public static ApiResponse<Boolean> openTable(OpenTableRequest req) {
        try{
            ITableServices tableServices = HttpHelper.CreateApiService(ITableServices.class);

            return new ApiResponse(tableServices.openTable(req));

        }catch(Exception ex){
            throw ex;
        }
    }

    public static ApiResponse<Boolean> closeTable(OpenTableRequest req) {
        try{
            ITableServices tableServices = HttpHelper.CreateApiService(ITableServices.class);

            return new ApiResponse(tableServices.closeTable(req));
        }catch(Exception ex){
            throw ex;
        }
    }

    public static ApiResponse<TableTransactionDetail> getTableDetail(OpenTableRequest req){
        try{
            ITableServices tableServices = HttpHelper.CreateApiService(ITableServices.class);

            return new ApiResponse(tableServices.getTableDetail(req));

        }catch(Exception ex){
            throw ex;
        }
    }

    public static ApiResponse<ArrayList<Order>> getAllOrderedFoodByRole(GetOrderedFoodRequest req){
        try{
            ITableServices tableServices = HttpHelper.CreateApiService(ITableServices.class);

            return new ApiResponse(tableServices.getAllOrderedFoodByRole(req));
        }catch(Exception ex){
            throw ex;
        }
    }

    public static ApiResponse<Boolean> addFoodsToTable(AddOrderToTableRequest req){
        try{
            ITableServices tableServices = HttpHelper.CreateApiService(ITableServices.class);

            return new ApiResponse(tableServices.addFoodsToTable(req));
        }
        catch(Exception ex){
            throw ex;
        }
    }

    public static ApiResponse<Boolean> changeStatusOfOrder(ChangeStatusOfOrderRequest req){
        try{
            ITableServices tableServices = HttpHelper.CreateApiService(ITableServices.class);

            return new ApiResponse(tableServices.changeStatusOfOrder(req));
        }
        catch(Exception ex){
            throw ex;
        }
    }

    public static ApiResponse<TableTransactionDetail> getOrderSummary(OpenTableRequest req){
        try{
            ITableServices tableServices = HttpHelper.CreateApiService(ITableServices.class);

            return new ApiResponse(tableServices.getOrderSummary(req));
        }
        catch(Exception ex){
            throw ex;
        }
    }

    public static ApiResponse<Boolean> addTables(AddNewTablesRequest req){
        try{
            ITableServices tableServices = HttpHelper.CreateApiService(ITableServices.class);

            return new ApiResponse(tableServices.addTables(req));
        }
        catch(Exception ex){
            throw ex;
        }
    }
}
