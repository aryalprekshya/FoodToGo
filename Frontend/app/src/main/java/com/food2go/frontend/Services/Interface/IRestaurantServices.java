package com.food2go.frontend.Services.Interface;


import com.food2go.frontend.models.BaseResponse;
import com.food2go.frontend.models.FoodImage;
import com.food2go.frontend.models.FoodItem;
import com.food2go.frontend.models.Restaurant;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IRestaurantServices {
    @GET("restaurant/getAll")
    Observable<BaseResponse<ArrayList<Restaurant>>> getAllRestaurant();

}
