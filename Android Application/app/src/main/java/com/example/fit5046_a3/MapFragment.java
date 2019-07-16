package com.example.fit5046_a3;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback{

    GoogleMap mGoogleMap;
    MapView mMapView;
    String address;
    float lng = 0;
    float lat = 0;
    ArrayList<String> parkname;
    ArrayList<Float> parklat;
    ArrayList<Float> parklng;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        //Allow main thread with network
        parklat = new ArrayList<>();
        parklng = new ArrayList<>();
        parkname = new ArrayList<>();


        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view=inflater.inflate(R.layout.fragment_map,null);
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
        address = pref.getString("address","");
        getlonglat();

        mMapView = view.findViewById(R.id.map);
        if (mMapView !=null)
        {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync( this);
        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap =  googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        System.out.println("Values on Map:"+lng+" "+lat);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("Home"));

        for (int i=0; i<parklat.size();i++)
        {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(parklat.get(i),parklng.get(i))).title(parkname.get(i))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }


        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(lat,lng)).zoom(12).bearing(0).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));

    }


    public void getlonglat(){
        try {
            JSONObject json = new JSONObject( GoogleSearchApi.getLongLat(address));
            lat = Float.valueOf(json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat"));
            lng = Float.valueOf(json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng"));


            JSONObject obj = new JSONObject(GoogleSearchApi.getParks(String.valueOf(lat), String.valueOf(lng)));
            JSONArray jsonArray = new JSONArray();
            jsonArray = obj.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++)
            {
                parklat.add(Float.valueOf(jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat")));
                parklng.add(Float.valueOf(jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng")));

                parkname.add(jsonArray.getJSONObject(i).getString("name"));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}