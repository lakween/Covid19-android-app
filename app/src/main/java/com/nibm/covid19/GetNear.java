package com.nibm.covid19;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNear extends AsyncTask<Object, String , String> {

    private String googleplaceData, url;
    private GoogleMap mMap;
    @Override
    protected String doInBackground(Object... objects) {
        mMap=(GoogleMap) objects[0];
        url=(String) objects[1];
        Downloadurl downloadurl =new Downloadurl();
        try {
            googleplaceData =downloadurl.readtheURL(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return googleplaceData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String ,String>> nearByPlaceList =null;
        DataParser dataParser =new DataParser();
        nearByPlaceList =dataParser.parse(s);
        DisplayNearbyPlaces(nearByPlaceList);
    }

    private void DisplayNearbyPlaces(List<HashMap<String ,String>> nearByPlaceList)
    {
        for (int i=0;i<nearByPlaceList.size();i++){
            MarkerOptions markerOptions =new MarkerOptions();
            HashMap<String,String> googleNearbyPlace = nearByPlaceList.get(i);
            String nameofPlace =googleNearbyPlace.get("place_name");
            String vicinity =googleNearbyPlace.get("vicinity");
            double lat =Double.parseDouble(googleNearbyPlace.get("lat"));
            double lng =Double.parseDouble(googleNearbyPlace.get("lng"));
            LatLng latLng= new LatLng(lat , lng);

            markerOptions.position(latLng);
            markerOptions.title(nameofPlace+":"+vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        }
    }


}
