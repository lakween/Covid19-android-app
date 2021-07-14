package com.nibm.covid19;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public  class MapsActivity extends FragmentActivity implements

        OnMapReadyCallback,

        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener

{

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private static final int Request_user_location_code =99;
    private double latitude , longitude;
    private int ProximityRadius = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
        {
                checkUserLocationPER();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onClick(View v){

            String hospital= "hospital";
        Object[] transferData = new Object[2];
        GetNear getnearbyplaces = new GetNear();



            switch(v.getId())
            {
                case R.id.search_address:
                    EditText addressField=(EditText) findViewById(R.id.location);
                    String address = addressField.getText().toString();
                    List<Address> addressList=null;
                    MarkerOptions userMarkerOption = new MarkerOptions();
                    if(!TextUtils.isEmpty(address)) {
                        Geocoder geocoder = new Geocoder(this);
                        try {
                            addressList = geocoder.getFromLocationName(address, 6);
                            if (address !=null)
                            {
                                for (int i=0; i<addressList.size();i++){
                                    Address userAddress = addressList.get(i);
                                    LatLng latLng= new LatLng(userAddress.getLatitude(),userAddress.getLongitude());

                                    userMarkerOption.position(latLng);
                                    userMarkerOption.title(address);
                                    userMarkerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                                    mMap.addMarker(userMarkerOption);
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                                }
                            }
                            else {
                                Toast.makeText(this,"Location not found",Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    else{
                        Toast.makeText(this,"please write any location name..",Toast.LENGTH_SHORT).show();

                    }
                    break;
                case R.id.hospital_nearby :
                    mMap.clear();
                    String url =getUrl(latitude,longitude,hospital);
                    transferData[0] =mMap;
                    transferData[1]=url;


                    getnearbyplaces.execute(transferData);
                    Toast.makeText(this,"Searching for Nearby Hospitals",Toast.LENGTH_SHORT).show();
                    Toast.makeText(this,"showing..",Toast.LENGTH_SHORT).show();

                 break;

            }

    }

    private  String getUrl(double latitude, double longitude,String nearbyPlace){

        StringBuilder googleURl =new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURl.append("location="+latitude+","+longitude);
        googleURl.append("&radius="+ProximityRadius);
        googleURl.append("&type="+ nearbyPlace);
        googleURl.append("&sensor=true");
        googleURl.append("&key="+ "AIzaSyB1HFVHUH1sluq6IMtnIu4qT2hFtO86LyU");
        Log.d("MapsActivity","url= "+ googleURl.toString());
        return googleURl.toString();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();

            mMap.setMyLocationEnabled(true);
        }
    }
 public boolean checkUserLocationPER()
 {
     if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
     {
         if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
         {
             ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_user_location_code );
         }
         else
         {
             ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_user_location_code );
         }
         return false;

     }
     else
     {
         return true;
     }
 }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case Request_user_location_code:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                    {
                        if(googleApiClient== null)
                        {
                            buildGoogleApiClient();

                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"permission Denied",Toast.LENGTH_SHORT).show();
                }
                return;
        }




    }

    protected synchronized void buildGoogleApiClient()
     {
               googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                       .addOnConnectionFailedListener(this)
                       .addApi(LocationServices.API)
                       .build();
               googleApiClient.connect();
     }
    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude= location.getLongitude();
      lastLocation=location;
      if(currentUserLocationMarker!=null)
      {
          currentUserLocationMarker.remove();
      }
      LatLng latLng= new LatLng(location.getLatitude(),location.getLongitude());
      MarkerOptions markerOptions =new MarkerOptions();
      markerOptions.position(latLng);
      markerOptions.title("User Current Location");
      markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

      currentUserLocationMarker= mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(14));
        if (googleApiClient!=null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (com.google.android.gms.location.LocationListener) this);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
