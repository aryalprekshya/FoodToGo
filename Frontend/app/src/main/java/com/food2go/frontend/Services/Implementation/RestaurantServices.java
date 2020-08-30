package com.food2go.frontend.Services.Implementation;


import com.food2go.frontend.Services.Interface.IRestaurantServices;
import com.food2go.frontend.Utilities.HttpHelper;
import com.food2go.frontend.models.ApiResponse;

public class RestaurantServices {
    public static ApiResponse getAllRestaurants(){
        try{
            IRestaurantServices resServices = HttpHelper.CreateApiService(IRestaurantServices.class);

            return new ApiResponse(resServices.getAllRestaurant());

        }catch(Exception ex){
            throw ex;
        }
    }
}

