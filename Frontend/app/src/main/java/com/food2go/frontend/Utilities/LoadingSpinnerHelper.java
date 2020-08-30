package com.food2go.frontend.Utilities;

import android.app.Activity;
import android.view.View;

import androidx.core.view.ViewCompat;

import com.food2go.frontend.R;


public class LoadingSpinnerHelper {
    public static void displayLoadingSpinner(View view){
        View loadingSpinner = view.findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(View.VISIBLE);
        ViewCompat.setTranslationZ(loadingSpinner, 5);
    }

    public static void hideLoadingSpinner(View view){
        view.findViewById(R.id.loading_spinner).setVisibility(View.INVISIBLE);
    }
}
