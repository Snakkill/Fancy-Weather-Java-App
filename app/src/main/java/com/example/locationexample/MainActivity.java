package com.example.locationexample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Toast;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends Activity {

    Button btnShowLocation;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    public static final String EXTRA_MESSAGE = "com.example.locationexample";
    // GPSTracker class
    GPSTracker gps;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_results:
                        Intent intent = new Intent(MainActivity.this, output.class);
                        EditText editText = (EditText) findViewById(R.id.Text);
                        String message = editText.getText().toString();
                        intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Weather", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_home:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_cal:

                        intent = new Intent(MainActivity.this, HistoryOutput.class);
                        editText = (EditText) findViewById(R.id.Text);
                        message = editText.getText().toString();
                        intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Calender", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });

    }



        public void getpoints(View V){

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnShowLocation = (Button) findViewById(R.id.button);

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();


                    EditText editText = (EditText) findViewById(R.id.Text);
                    editText.setText(latitude+","+longitude);

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });
    }



    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, output.class);
        EditText editText = (EditText) findViewById(R.id.Text);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}