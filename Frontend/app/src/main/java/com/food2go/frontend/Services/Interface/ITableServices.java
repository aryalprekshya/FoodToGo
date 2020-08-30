package com.food2go.frontend.Services.Interface;



import com.food2go.frontend.models.AddNewTablesRequest;
import com.food2go.frontend.models.AddOrderToTableRequest;
import com.food2go.frontend.models.BaseResponse;
import com.food2go.frontend.models.ChangeStatusOfOrderRequest;
import com.food2go.frontend.models.GetOrderedFoodRequest;
import com.food2go.frontend.models.OpenTableRequest;
import com.food2go.frontend.models.Order;
import com.food2go.frontend.models.Restaurant;
import com.food2go.frontend.models.Table;
import com.food2go.frontend.models.TableTransactionDetail;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ITableServices {

    @POST("table/getAllTables")
    Observable<BaseResponse<ArrayList<Table>>> getAllTables(@Body Restaurant restaurant);

    @POST("table/openTable")
    Observable<BaseResponse<Boolean>> openTable(@Body OpenTableRequest req);

    @POST("table/closeTable")
    Observable<BaseResponse<Boolean>> closeTable(@Body OpenTableRequest req);

    @POST("table/getTableDetail")
    Observable<BaseResponse<TableTransactionDetail>> getTableDetail(@Body OpenTableRequest req);

    @POST("table/getAllOrderedFoodByRole")
    Observable<BaseResponse<ArrayList<Order>>> getAllOrderedFoodByRole(@Body GetOrderedFoodRequest req);

    @POST("/table/addFoodsToTable")
    Observable<BaseResponse<Boolean>> addFoodsToTable(@Body AddOrderToTableRequest req);

    @POST("/table/changeStatusOfOrder")
    Observable<BaseResponse<Boolean>> changeStatusOfOrder(@Body ChangeStatusOfOrderRequest req);

    @POST("table/getOrderSummary")
    Observable<BaseResponse<TableTransactionDetail>> getOrderSummary(@Body OpenTableRequest req);

    @POST("table/addTables")
    Observable<BaseResponse<Boolean>> addTables(@Body AddNewTablesRequest req);

}
