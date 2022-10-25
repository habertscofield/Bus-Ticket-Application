package com.example.bus_ticketapplication.Maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.bus_ticketapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

        private GoogleMap mMap;
        LocationManager locationManager;
        private static final int LOCATION_REQUEST =500;
        private LatLng currentLocation;
        private LatLng destination;

        @Override
        protected void onCreate(final Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_maps);
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);

             // double distance =CalculationByDistance(currentLocation,destination);

            //   Toast.makeText(getApplicationContext(),"Distance is :" +distance,Toast.LENGTH_LONG).show();


                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                }


                // Check if network provider is available

                if (locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                        //get latitude
                                        double latitude = location.getLatitude();
                                        //get longitude
                                        double longitude = location.getLongitude();

                                        //instantiate class LatLang
                                        currentLocation = new LatLng(latitude, longitude);

                                        //instantiate class Geocoder
                                        Geocoder geocoder = new Geocoder(getApplicationContext());

                                        try {
                                                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                                String str = addressList.get(0).getLocality() + " ,";
                                                str += addressList.get(0).getCountryName();
                                                mMap.addMarker(new MarkerOptions().position(currentLocation).title(str));
                                                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }


                                }

                                @Override
                                public void onStatusChanged(String provider, int status, Bundle extras) {

                                }

                                @Override
                                public void onProviderEnabled(String provider) {

                                }

                                @Override
                                public void onProviderDisabled(String provider) {

                                }
                        });

                } else if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
                        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                        //get latitude
                                        double latitude = location.getLatitude();
                                        //get longitude
                                        double longitude = location.getLongitude();

                                        //instantiate class LatLang
                                        currentLocation = new LatLng(latitude, longitude);

                                        //instantiate class Geocoder
                                        Geocoder geocoder = new Geocoder(getApplicationContext());

                                        try {
                                                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                                String str = addressList.get(0).getLocality() + " ,";
                                                str += addressList.get(0).getCountryName();
                                                mMap.addMarker(new MarkerOptions().position(currentLocation).title(str));
                                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10.5f));
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }

                                }

                                @Override
                                public void onStatusChanged(String provider, int status, Bundle extras) {

                                }

                                @Override
                                public void onProviderEnabled(String provider) {

                                }

                                @Override
                                public void onProviderDisabled(String provider) {

                                }
                        });
                }

        }


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.getUiSettings().setZoomControlsEnabled(true);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST);
                        return;
                }
                // get current location

                //mMap.setMyLocationEnabled(true);


                //destination location

                Geocoder geocoder = new Geocoder(getApplicationContext());
                List<Address> addressList = null;
                try {
                        destination = new LatLng(-1.291532, 36.815803);
                        addressList = geocoder.getFromLocation(-1.291532, 36.815803, 1);
                        String location = addressList.get(0).getLocality() + " ,";
                        location += addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(destination).title(location));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
                } catch (IOException e) {
                        e.printStackTrace();
                }


                //String url = getRequestUrl(currentLocation,destination);
        }

/**
 //method request full url to get full url to request direction from google map api
 private String getRequestUrl(LatLng origin,LatLng destination)
 {
 //value of origin
 String str_origin = "origin" + origin.latitude +"," + origin.longitude;
 //value of destination
 String str_destination = "destination" + origin.latitude +","+origin.longitude;
 //set value enable sensor
 String sensor = "sensor-false";
 //Mode for find direction
 String mode = "mode=driving";
 //Build the full param
 String param = str_origin+"&" +str_destination +"&"+sensor+"&"+mode;
 //Output format
 String output = "json";
 //Create url to request
 String url = "https://maps.googleapis.com/map/api/directions" +output +"?"+param;
 return url;
 }
 **/
        /**
         //method to request directions using http url connection
         private String requestDirection(String reqUrl) throws IOException {
         String responseString = "";
         InputStream inputStream = null;
         HttpURLConnection httpURLConnection = null;
         try {
         URL url = new URL(reqUrl);
         httpURLConnection = (HttpURLConnection) url.openConnection();
         //Get the response result
         inputStream = httpURLConnection.getInputStream();
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         StringBuffer stringBuffer = new StringBuffer();
         String line = "";
         while ((line = bufferedReader.readLine()) != null)
         {
         stringBuffer.append(line);
         }
         responseString = stringBuffer.toString();
         bufferedReader.close();
         inputStreamReader.close();
         } catch (Exception e) {
         e.printStackTrace();
         }
         finally {
         if (inputStream != null)
         {
         inputStream.close();
         }
         httpURLConnection.disconnect();
         }
         return responseString;
         }
         **/
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                switch (requestCode) {
                        case LOCATION_REQUEST:
                                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                        Geocoder geocoder = new Geocoder(getApplicationContext());
                                        List<Address> addressList = null;
                                        try {
                                                LatLng destination = new LatLng(-1.291532, 36.815803);
                                                addressList = geocoder.getFromLocation(-1.291532, 36.815803, 1);
                                                String location = addressList.get(0).getLocality() + " ,";
                                                location += addressList.get(0).getCountryName();
                                                mMap.addMarker(new MarkerOptions().position(destination).title(location));
                                                mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                                break;
                }

        }

        /**  public class TaskRequestDirections extends AsyncTask<String,Void,String>
         {
         @Override
         protected String doInBackground(String... strings) {
         String responseString = "";
         try {
         responseString = requestDirection(strings[0])
         } catch (IOException e) {
         e.printStackTrace();
         }
         return responseString;
         }
         @Override
         protected void onPostExecute(String s) {
         super.onPostExecute(s);
         //parse json here
         }
         }
         **/

        public double CalculationByDistance(LatLng StartP, LatLng EndP) {
                int Radius=6371;//radius of earth in Km
                double lat1 = StartP.latitude;
            double lat2 = EndP.latitude;
                double lon1 = StartP.longitude;
                double lon2 = EndP.longitude;
                double dLat = Math.toRadians(lat2-lat1);
                double dLon = Math.toRadians(lon2-lon1);
                double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2);
                double c = 2 * Math.asin(Math.sqrt(a));
                double valueResult= Radius*c;
                double km=valueResult/1;
                DecimalFormat newFormat = new DecimalFormat("####");
                int kmInDec =  Integer.valueOf(newFormat.format(km));
                double meter=valueResult%1000;
                int  meterInDec= Integer.valueOf(newFormat.format(meter));
                Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

                return Radius * c;
        }

}