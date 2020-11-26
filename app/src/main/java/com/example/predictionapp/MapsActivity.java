package com.example.predictionapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ProgressDialog dialog;
    ArrayList<String> list;
    ArrayList<Double> latitude = new ArrayList<>();
    ArrayList<Double> longitude = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    class GetTask extends AsyncTask<String,Integer,Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MapsActivity.this);
            dialog.setMessage("Loading");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try{
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://192.168.1.71/get_data.php");
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();
                InputStream is = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String data = "";
                String line;
                while((line=reader.readLine())!=null){
                    data = data + line;
                }
                JSONObject jsonObject = new JSONObject(data);
                if(jsonObject.getString("response").equalsIgnoreCase("ok")){
                    list = new ArrayList<>();
                    JSONArray  array = new JSONArray("data");
                    for(int i=0;i<array.length();i++){
                        list.add(array.getString(i));
                    }
                    return true;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {

                for (int i = 0; i < latitude.size(); i++) {
                    LatLng marker = new LatLng(latitude.get(i), longitude.get(i));
                    mMap.addMarker(new MarkerOptions().position(marker).title("Accident Percentage: " + list.get(i)));
                    if (i == 0) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 12));
                    }

                }
            }
            else{
                Toast.makeText(MapsActivity.this,"Error getting data",Toast.LENGTH_SHORT).show();
            }
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

        latitude.add(30.878462);
        longitude.add(75.846624);

        latitude.add(30.878462);
        longitude.add(75.846624);


        latitude.add(30.878462);
        longitude.add(75.846624);

        latitude.add(30.878462);
        longitude.add(75.846624);


        latitude.add(30.878462);
        longitude.add(75.846624);

        latitude.add(30.878462);
        longitude.add(75.846624);

        latitude.add(30.878462);
        longitude.add(75.846624);

        latitude.add(30.878462);
        longitude.add(75.846624);


        latitude.add(30.878462);
        longitude.add(75.846624);

        latitude.add(30.878462);
        longitude.add(75.846624);


        new GetTask().execute();


    }
}
