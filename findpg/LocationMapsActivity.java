package com.example.kankan.findpg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocationMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location mLocation;
    double latitute,longtitute;
    private GetMyLocation myLocation;
    private String state,district,locality,pincode;

    public static final int LOCATION_REQUEST=10020;
    ArrayList<LatLng> listPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_maps);

        myLocation=new GetMyLocation(LocationMapsActivity.this);
        mLocation=myLocation.getLocation();
        latitute=mLocation.getLatitude();
        longtitute=mLocation.getLongitude();

        Bundle bundle=getIntent().getExtras();

        state=bundle.getString("MAPS_STATE");
        district=bundle.getString("MAPS_DISTERICT");
        locality=bundle.getString("MAPS_LOCALITY");
        pincode=bundle.getString("MAPS_PINCODE");



        //getRoomLocation();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listPoints=new ArrayList<>();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng ownPositionSE = new LatLng(latitute, longtitute);
        mMap.addMarker(new MarkerOptions().position(ownPositionSE).title("I am Here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ownPositionSE));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);

        listPoints.add(ownPositionSE);
        getRoomLocation();

        if(listPoints.size()==2)
        {
            //Create The url to get request from first marker to second marker...
            String url=getRequestUrl(listPoints.get(0),listPoints.get(1));
            TaskRequestDirection taskRequestDirection=new TaskRequestDirection();
            taskRequestDirection.execute(url);

        }


    }
    public void getRoomLocation()
    {
        String location = locality+","+pincode;
        List<Address> addressList = null;
        MarkerOptions mMarkerOpction = new MarkerOptions();
        if (!location.equals("")) {
            Geocoder geocoder = new Geocoder(LocationMapsActivity.this);
            try {
                addressList = geocoder.getFromLocationName(location, 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < addressList.size(); i++) {
                Address myAddress = addressList.get(i);
                LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                listPoints.add(latLng);
                mMarkerOpction.position(latLng);
                mMarkerOpction.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                mMarkerOpction.title("Here is the Room");
                mMap.addMarker(mMarkerOpction);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.getUiSettings().setZoomControlsEnabled(true);
            }



        }
    }

    private String getRequestUrl(LatLng origin, LatLng dest) {
        //Vlaue of origin
        String str_org="origin="+origin.latitude+","+origin.longitude;
        //value of destination..
        String str_des="destination="+dest.latitude+","+dest.longitude;
        //set Value enable the sensor
        String sensor="sensor=false";
        //Mode for the find direction...
        String mode="mode=walking";
        //Build the full params..
        String param=str_org+"&"+str_des+"&"+sensor+"&"+mode;
        //output Format..
        String output="json";
        //create url to request...
        String url="https://maps.googleapis.com/maps/api/directions/"+output+"?"+param;
        return url;
    }
    private String requestDirection(String reqUrl) throws IOException {
        String responseString="";
        InputStream inputStream=null;
        HttpURLConnection httpURLConnection=null;
        try
        {
            URL url=new URL(reqUrl);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.connect();

            //get the response Result...
            inputStream=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer=new StringBuffer();
            String line="";

            while((line=bufferedReader.readLine())!=null)
            {
                stringBuffer.append(line);
            }
            responseString=stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        }catch(Exception e){

        }
        finally {
            if(inputStream !=null)
            {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case LOCATION_REQUEST:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
                break;

        }
    }

    public class TaskRequestDirection extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String responseString="";
            try {
                responseString = requestDirection(strings[0]);
            }
            catch (Exception e)
            {

            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //parse json here...
            TaskParser taskParser=new TaskParser();
            taskParser.execute(s);



        }
        public class TaskParser extends AsyncTask<String,Void,List<List<HashMap<String,String>>>>
        {

            @Override
            protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
                JSONObject jsonObject=null;
                List<List<HashMap<String, String>>> routes=null;
                try{
                    jsonObject=new JSONObject(strings[0]);
                    GoogleMapDirection directionParser=new GoogleMapDirection();
                    routes=directionParser.parse(jsonObject);


                }catch (Exception e){

                }
                return routes;
            }

            @Override
            protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
                //Get list route and display it into the map..
                ArrayList points=null;
                PolylineOptions polylineOptions=null;

                for(List<HashMap<String, String>> path: lists)
                {
                    points=new ArrayList();
                    polylineOptions=new PolylineOptions();

                    for(HashMap<String, String> point:path )
                    {
                        double lat=Double.parseDouble(point.get("lat"));
                        double lon=Double.parseDouble(point.get("lon"));

                        points.add(new LatLng(lat,lon));
                    }

                    polylineOptions.addAll(points);
                    polylineOptions.width(15);
                    polylineOptions.color(Color.BLUE);
                    polylineOptions.geodesic(true);

                }

                if(polylineOptions !=null)
                {
                    mMap.addPolyline(polylineOptions);
                }
                else
                {
                    Toast.makeText(LocationMapsActivity.this,"Direction Not Found",Toast.LENGTH_LONG).show();
                }

            }
        }
    }


}
