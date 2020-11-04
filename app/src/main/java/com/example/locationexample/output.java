package com.example.locationexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.locationexample.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Date;
import java.util.Timer;

public class output extends AppCompatActivity implements OnMapReadyCallback {
    public static final String EXTRA_MESSAGE = "com.example.locationexample";
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_results:
                        Toast.makeText(output.this, "Weather", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_overlay:
                        Toast.makeText(output.this, "Overlay", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_home:
                        Toast.makeText(output.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(output.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_cal:

                        intent = new Intent(output.this, HistoryOutput.class);
                        startActivity(intent);
                        Toast.makeText(output.this, "Calender", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });



        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        final TextView coordsOut = findViewById(R.id.Textoutput1);
        final TextView TempOut = findViewById(R.id.Temp);
        final TextView TempBanner = findViewById(R.id.tempbanner);





        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url="";
        if ((message.length()) >=18 ){

            String[] words = message.split(",");
            String lat= words[0];
            String lon= words[1];
            url="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=74263b9a49d1177a1f44424e8fad66d9";
        }
        else if ((message.length()) ==5){

            url = "https://api.openweathermap.org/data/2.5/weather?zip="+message+",us&appid=74263b9a49d1177a1f44424e8fad66d9";

        }
        else {

            url="https://api.openweathermap.org/data/2.5/weather?q="+message+"&appid=74263b9a49d1177a1f44424e8fad66d9";

        }

        //String url = "https://api.openweathermap.org/data/2.5/weather?zip=33076,us&appid=74263b9a49d1177a1f44424e8fad66d9";
//
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        coordsOut.setText(response);
                        TextView reply = (TextView) findViewById(R.id.Textoutput1);
                        String output = reply.getText().toString();
                        try {
                            JSONObject reader = new JSONObject(output);
                            JSONObject coord  = reader.getJSONObject("coord");
                            JSONObject main  = reader.getJSONObject("main");
                            JSONObject wind  = reader.getJSONObject("wind");
                            JSONObject sys  = reader.getJSONObject("sys");
                            String lat = coord.getString("lat");
                            String lon = coord.getString("lon");
                            String T_high = main.getString("temp_max");
                            String T_low = main.getString("temp_min");
                            String T_feel = main.getString("feels_like");
                            String pressure = main.getString("pressure");
                            String humidity = main.getString("humidity");
                            String speed = wind.getString("speed");

                            String sunrise = sys.getString("sunrise");
                            String sunset = sys.getString("sunset");

                            Double temp=Double.parseDouble(T_high);                 //conversions of data
                            temp= Double.valueOf(Math.round(temp*9/5-459.67   ));
                            T_high= String.valueOf(temp);


                            temp=Double.parseDouble(T_low);
                            temp= Double.valueOf(Math.round(temp*9/5-459.67   ));
                            T_low= String.valueOf(temp);

                            temp=Double.parseDouble(T_feel);
                            temp= Double.valueOf(Math.round(temp*9/5-459.67   ));
                            T_feel= String.valueOf(temp);

                            int temp2=Integer.parseInt(sunset);
                            Time date = new Time(temp2);
                            sunset= String.valueOf(date);

                            temp2=Integer.parseInt(sunrise);
                            Time date2 = new Time(temp2);
                            sunrise= String.valueOf(date2);


                            TempBanner.setText("IT FEELS LIKE "+T_feel+"°F Today!!!");
                            TempOut.setText("Temperature                 High "+T_high+"°F\n                                          Low  "+T_low+"°F \n Humidity:                           "+                        humidity+" % \n Pressure:                         "+                        pressure
                                    +"hpa \n Wind Speed                         "+speed+"mph \n Sunrise:                         "+sunrise+" AM \n Sunset:                         "+sunset+"pm \n Coordinates:                  "+lon+",  "+lat+"}");
                            coordsOut.setText(" Coordinates: {"+lon+","+lat+"}");

                            Intent intent = new Intent(output.this, HistoryOutput.class);

                            String message = output;

                            intent.putExtra(EXTRA_MESSAGE, message);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(output);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                coordsOut.setText("That didn't work! You may of entered the format incorrectly. Try again or try auto fill location");
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