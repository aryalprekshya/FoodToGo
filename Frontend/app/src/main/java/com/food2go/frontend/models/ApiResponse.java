package com.food2go.frontend.models;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ApiResponse<T> {
    private Observable<BaseResponse<T>> response;

    public Observable<BaseResponse<T>> GetResponse(){
        return this.response;
    }
    public ApiResponse(Observable<BaseResponse<T>> response){
        this.response = response;
    }

    public void Subscribe(Consumer<BaseResponse> onSuccess, Consumer<Throwable> onError){
        this.response.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError);
    }
}
