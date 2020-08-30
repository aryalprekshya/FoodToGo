package com.food2go.frontend.navigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.food2go.frontend.R;
import com.food2go.frontend.models.Restaurant;
import com.food2go.frontend.models.Table;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.MODE_PRIVATE;

public class MapsFragment extends Fragment implements View.OnClickListener,OnMapReadyCallback, LocationListener, GoogleMap.OnMapClickListener {

    //Constants
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 13f;
    public LocationManager locationManager;
    boolean mLocationPermissionGranted = false;
    private LatLng myLocation;
    private boolean alreadyZoomedToMyPlace = false;

    String TAG="mapsFragment";

    private GoogleMap map;

    private AutoCompleteTextView mSearchText;
    //private LatLng myLocation;
    private  View view;

    private NavController navController;

    private Restaurant selectedRestaurant;

    private ArrayList<Restaurant> listRestaurant;
    private ArrayList<Table> tables;

    public static final String APP_PREF_KEY = "app_pref";
    public static final String USER_NAME = "userName";
    public static final String PASSWORD = "password";
    public static final String RESTAURANT = "restaurant";
    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view= view;
        getLocationPermission();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(MapsFragment.this);
        }
        preferences = getContext().getSharedPreferences(APP_PREF_KEY,MODE_PRIVATE);

        view.findViewById(R.id.closeButton).setOnClickListener(this);
        view.findViewById(R.id.make_order).setOnClickListener(this);

        navController = Navigation.findNavController(view);

    listRestaurant = new ArrayList<>();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            int grantResult = grantResults[i];

            if (permission.equals(ACCESS_FINE_LOCATION) || permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    break;
                }
            }
        }
        if (mLocationPermissionGranted = true) {
            if (ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Location permission denied.", Toast.LENGTH_SHORT).show();
                return;
            }
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setMapToolbarEnabled(true);
            getDeviceLocation();
        } else {
            Toast.makeText(getContext(), "Location permission denied.", Toast.LENGTH_SHORT).show();
        }
    }



    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                //initMap();
                //getDeviceLocation();
            }else{
                requestPermissions(permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            requestPermissions(permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);

            map.getUiSettings().setZoomControlsEnabled(true);
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setMapToolbarEnabled(true);

        }
        map.setOnMapClickListener(this);


        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, (LocationListener) this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, this);


        //  String url = "https://api.yelp.com/v3/autocomplete?text="+searchString+"&latitude="+ (int)myLocation.latitude +"&longitude="+myLocation.longitude;
        String url = "http://10.0.0.222:8080/restaurant/getAll";
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //suggestions.clear();
                            JSONArray terms = response.getJSONArray("data");
                            for (int i = 0; i < terms.length(); i++) {
                                JSONObject bsn = terms.getJSONObject(i);
                                String name = bsn.getString("name");
                                Log.d(TAG, "onResponse: "+ name);
                                //suggestions.add(term);
                                double latitude = Double.parseDouble(bsn.getString("latitude"));
                                double longitude = Double.parseDouble(bsn.getString("longitude"));
                                Restaurant restaurant = new Restaurant(bsn.getInt("code"),bsn.getString("name"),bsn.getString("address"),latitude,longitude);

                                listRestaurant.add(restaurant);
                            }

                            for (Restaurant res:listRestaurant) {
                                setMarkerInMap(new LatLng(res.getLatitude(),res.getLongitude()),res.getName());
                            }

                            moveCamera(myLocation,9F);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("aaq", "onErrorResponse: " + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                //  params.put("Authorization", getString(R.string.ACCESS_TOKEN));
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }


    private void getDeviceLocation() {
        FusedLocationProviderClient mFusedLocationProverClient = LocationServices.getFusedLocationProviderClient(getContext());
        try {
            if (mLocationPermissionGranted) {

                mFusedLocationProverClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            if (!alreadyZoomedToMyPlace) {
                                moveCamera(myLocation, DEFAULT_ZOOM);
                                alreadyZoomedToMyPlace = true;
                            }
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    //Move map camera according to location
    private void moveCamera(LatLng latLng, float zoom) {
        if (map==null || latLng==null) return;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }


    //Set Markers on map
//    private void setMarkerInMap(LatLng latLng, String title, String snippet) {
//        //I used snippet to location id. it is a workaround as marker doens't give location id.
//        MarkerOptions options = new MarkerOptions().position(latLng).title(title).snippet(snippet);
//        map.addMarker(options);
//
//        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                getPlaceDetails(marker.getSnippet());
//                return false;
//            }
//        });
//
//    }

    public void hideKeyboard() {
        View vw = getActivity().getCurrentFocus();
        if (vw != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(vw.getWindowToken(), 0);
        }

    }

    //Set dummy markers data on map
    private void setDummyData(LatLng current){
        setMarkerInMap(new LatLng(current.latitude+0.003,current.longitude+0.005),"Cafe");

        setMarkerInMap(new LatLng(current.latitude+0.002,current.longitude-0.005),"Cafe");

        setMarkerInMap(new LatLng(current.latitude-0.005,current.longitude-0.005),"Cafe");
    }

    //Set Markers on map
    private void setMarkerInMap(LatLng latLng, String title) {
        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        map.addMarker(options);

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Restaurant restaurant = getRestaurantFromList(marker.getTitle());
                selectedRestaurant = restaurant;
                if(restaurant!=null){
                    View v = view.findViewById(R.id.details);
                    v.setVisibility(View.VISIBLE);
                    TextView address = view.findViewById(R.id.address);
                    TextView distance = view.findViewById(R.id.distance);
                    TextView name = view.findViewById(R.id.name);

                    address.setText(restaurant.getAddress());
                    name.setText(restaurant.getName());
                    double dist = distance(restaurant.getLatitude(),restaurant.getLongitude(),myLocation.longitude,myLocation.longitude,"M");
                    distance.setText(dist + " KM");
                }
                return false;
            }
        });
        
        
  /*
map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Business business = getBusinessFromList(marker.getTitle());
                if(business!=null ){
                    Log.d(TAG, "match found: "+business.getName());
                    View v = findViewById(R.id.details);
                    v.setVisibility(View.VISIBLE);
                    ImageView imageView = findViewById(R.id.imageView);

                    TextView title = findViewById(R.id.title);
                    TextView phone = findViewById(R.id.phone);
                    TextView address = findViewById(R.id.address);
                    TextView isopen = findViewById(R.id.openStatus);

                    title.setText(business.getName());
                    phone.setText(business.getPhone());
                    isopen.setText(business.getIs_closed()?"Closed":"Open");
                    address.setText(business.getAddress());
                    if(business.getImage_url() != null) {
                        Picasso.get().load(business.getImage_url()).into(imageView);
                    }
                }
                return false;
            }
        });
        */
    }

    private Restaurant getRestaurantFromList(String title) {
        for (Restaurant business: listRestaurant)
        {
            Log.d(TAG, "getBusinessFromList: "+ business.getName()+" "+title);
            if(business.getName().equals(title)){
                return business;
            }
        }
        return null;
    }

    //Hide the details of business
//    public void removeDetailView(View view){
//        View v = view.findViewById(R.id.details);
//        v.setVisibility(View.GONE);
//    }

    //From online
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.closeButton:
                View viw = view.findViewById(R.id.details);
                viw.setVisibility(View.GONE);
                break;
            case R.id.make_order:
               // Toast.makeText(getContext(),"clicked",Toast.LENGTH_SHORT).show();
                if(selectedRestaurant != null){
                    SharedPreferences.Editor editor = preferences.edit();
                    System.out.println(selectedRestaurant.getCode());
                    editor.putInt(RESTAURANT, selectedRestaurant.getCode());
                    editor.apply();
                }
                navController.navigate(R.id.action_navigation_map_to_navigation_order);
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(getActivity()==null) return;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //map.setMyLocationEnabled(true);
        myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        if (!alreadyZoomedToMyPlace) {
            alreadyZoomedToMyPlace = true;
            moveCamera(myLocation, DEFAULT_ZOOM);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        getDeviceLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }



    @Override
    public void onMapClick(LatLng latLng) {

        //If there is any fragments hide them when clicking on any points on map.
        Log.d(TAG, "onMapClick: I am clicked");

    }
}