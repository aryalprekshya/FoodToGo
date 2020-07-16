package com.food2go.frontend.navigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.food2go.frontend.R;
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

public class MapsFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    //Constants
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 13f;

    boolean mLocationPermissionGranted = false;

    private GoogleMap map;

    private AutoCompleteTextView mSearchText;
    //private LatLng myLocation;
    private  View view;

    private NavController navController;
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

        view.findViewById(R.id.closeButton).setOnClickListener(this);
        view.findViewById(R.id.make_order).setOnClickListener(this);

        navController = Navigation.findNavController(view);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            int grantResult = grantResults[i];

            if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION) || permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    break;
                }
            }
        }
        if (mLocationPermissionGranted = true) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               Toast.makeText(getContext(),"Location permission denied.",Toast.LENGTH_SHORT).show();
                return;
            }
            map.setMyLocationEnabled(true);
            getDeviceLocation();
        }else{
            Toast.makeText(getContext(),"Location permission denied.",Toast.LENGTH_SHORT).show();
        }
    }

    //Dont init map until fragment location is accessed
//    private void initMap() {
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment != null) {
//            mapFragment.getMapAsync(MapsFragment.this);
//        }
//
//    }

//    private OnMapReadyCallback callback = new OnMapReadyCallback() {
//
//        /**
//         * Manipulates the map once available.
//         * This callback is triggered when the map is ready to be used.
//         * This is where we can add markers or lines, add listeners or move the camera.
//         * In this case, we just add a marker near Sydney, Australia.
//         * If Google Play services is not installed on the device, the user will be prompted to
//         * install it inside the SupportMapFragment. This method will only be triggered once the
//         * user has installed Google Play services and returned to the app.
//         */
//        @Override
//        public void onMapReady(GoogleMap googleMap) {
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        }
//    };


    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                //initMap();
                getDeviceLocation();
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
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);
        }
    }

    private void getDeviceLocation(){
       // System.out.println("got loaction");


        FusedLocationProviderClient mFusedLocationProverClient = LocationServices.getFusedLocationProviderClient(getContext());
        try{
            if(mLocationPermissionGranted){
//                Task location = mFusedLocationProverClient.getLastLocation();
//                location.addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if(task.isSuccessful()){
//                            Location currentlocation = (Location) task.getResult();
//                            myLocation = new LatLng(currentlocation.getLatitude(),currentlocation.getLongitude());
//                            moveCamera(myLocation,"My Location",DEFAULT_ZOOM);
//                        }else{
//                            Toast.makeText(getActivity(), "unable to get the location", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
                mFusedLocationProverClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                      //  Location currentlocation = (Location) task.getResult();
                        if(location!=null) {
                            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            moveCamera(myLocation, "My Location", DEFAULT_ZOOM);
                        }
                    }
                });
            }

        }catch(SecurityException e){
            e.printStackTrace();
        }

    }

    //Move map camera according to location
    private void moveCamera(LatLng latLng, String title, float zoom){
        //Log.d(TAG, "moveCamera: moving the camera to lat: "+latLng.latitude + " long: "+ latLng.longitude  );
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        //setMarkerInMap(latLng,title);
        setDummyData(latLng);
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
                View v = view.findViewById(R.id.details);
                v.setVisibility(View.VISIBLE);
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

    //Hide the details of business
//    public void removeDetailView(View view){
//        View v = view.findViewById(R.id.details);
//        v.setVisibility(View.GONE);
//    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.closeButton:
                View viw = view.findViewById(R.id.details);
                viw.setVisibility(View.GONE);
                break;
            case R.id.make_order:
               // Toast.makeText(getContext(),"clicked",Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_navigation_map_to_navigation_order);
                break;
        }
    }
}