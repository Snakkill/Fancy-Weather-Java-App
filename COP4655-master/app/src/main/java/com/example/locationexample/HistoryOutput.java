package com.example.locationexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

public class HistoryOutput extends AppCompatActivity implements OnMapReadyCallback{
 GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        // Get the Intent that started this activity and extract the string
        final Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject reader = null;
        try {
            reader = new JSONObject(message);
            JSONObject coord = reader.getJSONObject("coord");
            String lat = coord.getString("lat");
            String lon = coord.getString("lon");
            JSONObject sys = reader.getJSONObject("sys");
            String sunrise = sys.getString("sunrise");

            System.out.println(sunrise);
            String url = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + lat + "&lon=" + lon + "&dt=" + sunrise + "&appid=74263b9a49d1177a1f44424e8fad66d9";
            TextView response= findViewById(R.id.H_results);
            response.setText(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }

            final TextView coordsOut = findViewById(R.id.H_results);
            final TextView day1 = findViewById(R.id.day1);
            final TextView day2 = findViewById(R.id.day2);
            final TextView day3 = findViewById(R.id.day3);
            final TextView day4 = findViewById(R.id.day4);
            final TextView day5 = findViewById(R.id.day5);

            TextView response= findViewById(R.id.H_results);
            TextView reply = (TextView) findViewById(R.id.H_results);
            String url = reply.getText().toString();
            // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                       coordsOut.setText(response);
                        TextView reply = (TextView) findViewById(R.id.H_results);
                         String output = reply.getText().toString();
                        coordsOut.setText("Past 5 Days");
                        try {
                            JSONObject reader = new JSONObject(output);
                            System.out.println(output);

                            JSONObject currentObj  = reader.getJSONObject("current");
                            String current=currentObj.getString("dt");
                            String currentSun=currentObj.getString("sunrise");
                            String currentSet=currentObj.getString("sunset");
                            String currentTemp=currentObj.getString("temp");
                            String currentF_Temp=currentObj.getString("feels_like");
                            String currentPress=currentObj.getString("pressure");
                            String currentHum=currentObj.getString("humidity");
                            String currentWind=currentObj.getString("wind_speed");
                            String currentDeg=currentObj.getString("wind_deg");
                            System.out.println(current);


                            Double temp=Double.parseDouble(currentTemp);                 //conversions of data
                            temp= Double.valueOf(Math.round(temp*9/5-459.67   ));
                            currentTemp= String.valueOf(temp);


                            temp=Double.parseDouble(currentF_Temp);
                            temp= Double.valueOf(Math.round(temp*9/5-459.67   ));
                            currentF_Temp= String.valueOf(temp);


                            int temp2=Integer.parseInt(currentSet);
                            Time date = new Time(temp2);
                            currentSet= String.valueOf(date);

                            temp2=Integer.parseInt(currentSun);
                            Time date2 = new Time(temp2);
                            currentSun= String.valueOf(date2);


                            day1.setText("Temperature             Feels Like "+currentF_Temp+"°F\n                                          Current  "+currentTemp+"°F \n Humidity:                           "+                        currentHum+" % \n Pressure:                           "+                        currentPress
                                    +"hpa \n Wind Speed                         "+currentWind+"mph \n Wind Degree                        "+currentDeg+ "\n Sunrise:                         "+currentSun+" AM \n Sunset:                          "+currentSet+"pm \n ");

                            Integer currentNum= Integer.parseInt(current);
                            currentNum=currentNum-(86400);
                            String current2=currentNum.toString();
                            System.out.println(current2);
                            JSONObject currentObj2  = reader.getJSONObject("1604455200");
                            String currentSun2=currentObj2.getString("sunrise");
                            String currentSet2=currentObj2.getString("sunset");
                            String currentTemp2=currentObj2.getString("temp");
                            String currentF_Temp2=currentObj2.getString("feels_like");
                            String currentPress2=currentObj2.getString("pressure");
                            String currentHum2=currentObj2.getString("humidity");
                            String currentWind2=currentObj2.getString("wind_speed");
                            String currentDeg2=currentObj2.getString("wind_deg");


                            temp = Double.parseDouble(currentTemp2);
                            temp= Double.valueOf(Math.round(temp*9/5-459.67   ));
                            currentTemp2= String.valueOf(temp);


                            temp=Double.parseDouble(currentF_Temp2);
                            temp= Double.valueOf(Math.round(temp*9/5-459.67   ));
                            currentF_Temp2= String.valueOf(temp);


                            temp2 = Integer.parseInt(currentSet2);
                            date = new Time(temp2);
                            currentSet2= String.valueOf(date);

                            temp2=Integer.parseInt(currentSun2);
                            date2 = new Time(temp2);
                            currentSun2= String.valueOf(date2);
                            day2.setText("Temperature             Feels Like "+currentF_Temp2+"°F\n                                          Current  "+currentTemp2+"°F \n Humidity:                           "+                        currentHum2+" % \n Pressure:                           "+                        currentPress2
                                    +"hpa \n Wind Speed                         "+currentWind2+"mph \n Wind Degree                        "+currentDeg2+ "\n Sunrise:                         "+currentSun2+" AM \n Sunset:                          "+currentSet2+"pm \n ");
                            day3.setText("$$$$$$");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("Map on Ready");
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(26, -80);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}






