package com.healthtracker.empoweringtechnologies.healthtracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationServices;

import android.location.Location;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.app.Activity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;


public class NearestMedicalLocation extends AppCompatActivity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback {

    public TextView location;
    public ListView medicallocationlistview;
    public double longitude;
    public double latitude;
    public Geocoder geocoder;
    public List<HashMap<String, String>> mLocationsList = new ArrayList<>();
    private static final String KEY_NAME = "name";
    private static final String KEY_DISTANCE = "distance";
    public MedicalLocationAdapter medicallocationadapater;
    public String JSON_String;
    public List<Place> myPlaces;
    public JSONObject jsonobject;
    public JSONArray jsonarray;

    //List<Address> addresses;
    List<android.location.Address> addresses;


    private static final long ONE_MIN = 1000 * 60;
    private static final long TWO_MIN = ONE_MIN * 2;
    private static final long FIVE_MIN = ONE_MIN * 5;
    private static final long POLLING_FREQ = 1000 * 30;
    private static final long FASTEST_UPDATE_FREQ = 1000 * 5;
    private static final float MIN_ACCURACY = 25.0f;
    private static final float MIN_LAST_READ_ACCURACY = 500.0f;
    private LocationRequest mLocationRequest;
    private Location mBestReading;
    public SupportMapFragment mapFragment;

    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_medical_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        location = (TextView) findViewById(R.id.lblLocationInfo);
        medicallocationlistview = (ListView) findViewById(R.id.MedicalLocationListView);
        medicallocationadapater = new MedicalLocationAdapter(this, R.layout.medicallocation);
        medicallocationlistview.setAdapter(medicallocationadapater);

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(POLLING_FREQ);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_FREQ);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /* listener for clicks on the avaivity data to offer a dialog to edit or delete */
        medicallocationlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> listView, View view,
                                    int position, long id) {
                final int RoWIDEdit = (int) medicallocationadapater.getItemId(position);


                MedicalLocation ml = (MedicalLocation) medicallocationlistview.getItemAtPosition(RoWIDEdit);
                final String medlocation = ml.get_medicallocation();

                final ListView lv = (ListView) medicallocationlistview;

                //Listener for selection of hospital, navigates to google maps for driving directions
                AlertDialog.Builder dialog = new AlertDialog.Builder(NearestMedicalLocation.this);
                dialog.setTitle("Driving Directions");
                dialog.setMessage("Do you want driving direction?");
                dialog.setNegativeButton("Cancel", null);
                dialog.setNeutralButton("Yes", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String url = "https://www.google.com/maps/dir/Current+Location/" + medlocation;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public void onConnected(Bundle dataBundle) {
        // Get first reading. Get additional location updates if necessary
        if (servicesAvailable()) {
            // Get best last location measurement meeting criteria
            mBestReading = bestLastKnownLocation(MIN_LAST_READ_ACCURACY, FIVE_MIN);

            if (null == mBestReading
                    || mBestReading.getAccuracy() > MIN_LAST_READ_ACCURACY
                    || mBestReading.getTime() < System.currentTimeMillis() - TWO_MIN) {

                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

                } catch (SecurityException ex) {

                }


                // Schedule a runnable to unregister location listeners
                Executors.newScheduledThreadPool(1).schedule(new Runnable() {

                    @Override
                    public void run() {
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, NearestMedicalLocation.this);
                    }

                }, ONE_MIN, TimeUnit.MILLISECONDS);
            }
            latitude = mBestReading.getLatitude();
            longitude = mBestReading.getLongitude();
            String msg = "\nLatitude: " + latitude + "  " + "Longitude: " + longitude;
            msg = DisplayAddress() + msg;

            location.setText(msg);
            DisplayNearbyHospitals();
            mapFragment.getMapAsync(this);

        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();

        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        // Determine whether new location is better than current best
        // estimate
        if (null == mBestReading || location.getAccuracy() < mBestReading.getAccuracy()) {
            mBestReading = location;

            if (mBestReading.getAccuracy() < MIN_ACCURACY) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private Location bestLastKnownLocation(float minAccuracy, long minTime) {
        Location bestResult = null;
        float bestAccuracy = Float.MAX_VALUE;
        long bestTime = Long.MIN_VALUE;
        Location mCurrentLocation = null;

        // Get the best most recent location currently available
        try {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException ex) {

        }

        if (mCurrentLocation != null) {
            float accuracy = mCurrentLocation.getAccuracy();
            long time = mCurrentLocation.getTime();

            if (accuracy < bestAccuracy) {
                bestResult = mCurrentLocation;
                bestAccuracy = accuracy;
                bestTime = time;
            }
        }

        // Return best reading or null
        if (bestAccuracy > minAccuracy || bestTime < minTime) {
            return null;
        } else {
            return bestResult;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private boolean servicesAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (ConnectionResult.SUCCESS == resultCode) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0).show();
            return false;
        }
    }

    public void GoHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public String DisplayAddress() {

        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException ex) {

        }
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        return address + " " + city + " " + state + " " + postalCode;
    }

    public void DisplayNearbyHospitals() {
        GetPlaces gp = new GetPlaces(this, medicallocationlistview);
        gp.execute();        //gp.GetHospitals();
    }

    public class GetPlaces extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;
        private Context context;
        private String[] placeName;
        private String[] imageUrl;
        private ListView listView;
        MedicalLocationAdapter medicallocationadapater;

        public GetPlaces(Context context, ListView listView) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.listView = listView;
        }

        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //dialog = new ProgressDialog(context);
            //dialog.setCancelable(true);
            //dialog.setMessage("Loading..");
            //dialog.isIndeterminate();
            //dialog.show();
        }

        @Override
        public void onPostExecute(Void v) {
            super.onPostExecute(v);
            medicallocationadapater = new MedicalLocationAdapter(context, R.layout.medicallocation);
            medicallocationlistview.setAdapter(medicallocationadapater);
            try {
                jsonobject = new JSONObject(JSON_String);
                jsonarray = jsonobject.getJSONArray("results");
                int count = 0;
                String location;
                String distance;
                while (count < jsonarray.length()) {
                    JSONObject JO = jsonarray.getJSONObject(count);
                    location = JO.getString("name");
                    MedicalLocation medicallocation = new MedicalLocation(location);
                    medicallocationadapater.add(medicallocation);
                    count++;
                }
            } catch (Exception ex) {
                Log.e("test", "Error processing Places API URL" + ex.getMessage(), ex);
            }

        }


        @Override
        public Void doInBackground(Void... arg0) {


            String placesURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                    "json?location=" + latitude + "," + longitude +
                    "&radius=10000&sensor=true" +
                    "&types=hospital" +
                    //"&key=AIzaSyCQueIV2Cpq-mVeX8_zZzDqOicldRAQxz8";
                    "&key=AIzaSyDFL-UrImiFhknTtU0MZXKO479sFEgyHl8";
            StringBuilder placesBuilder = new StringBuilder();
            //for (String placeSearchURL : placesURL) {
            try {

                //URL requestUrl = new URL(placeSearchURL);
                URL requestUrl = new URL(placesURL);
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setRequestMethod("GET");

                try {
                    connection.connect();
                } catch (Exception ex) {
                    Log.i("test", "Unsuccessful HTTP Response Code: " + ex.getMessage());
                }

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader reader = null;

                    InputStream inputStream = connection.getInputStream();
                    if (inputStream == null) {
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {

                        placesBuilder.append(line + "\n");
                    }

                    if (placesBuilder.length() == 0) {
                        return null;
                    }

                    JSON_String = placesBuilder.toString();


                    Log.d("test", placesBuilder.toString());


                } else {
                    Log.i("test", "Unsuccessful HTTP Response Code: " + responseCode);
                }
            } catch (MalformedURLException e) {
                Log.e("test", "Error processing Places API URL", e);
            } catch (IOException e) {
                Log.e("test", "Error connecting to Places API", e);
            }
            //}
            return null;
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng location = new LatLng(latitude, longitude);

        try {
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));

            map.addMarker(new MarkerOptions()
                    .title("Current Location")
                    .snippet("Current Location")
                    .position(location));
        } catch (SecurityException ex) {

        }
    }
}
