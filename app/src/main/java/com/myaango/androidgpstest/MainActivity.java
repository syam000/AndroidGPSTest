package com.myaango.androidgpstest;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationManager.LocationListener {

    private LocationManager locationManager;
    private TextView locatioInfoTextView;
    private static final int LOCATION_ACCESS_REQUEST_CODE = 665;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locatioInfoTextView = (TextView) findViewById(R.id.location_updates);
        setup();
    }

    public void setup() {

        locationManager = new LocationManager(this);
        locationManager.setLocationListener(this);
        locationManager.connect();
    }

    public void onClickLocateMe(View view){

        Location location = locationManager.getLastknownLocation();
        if (location != null) {
            locatioInfoTextView.setText("Latitude " + String.valueOf(location.getLatitude()) + " Longitude " + String.valueOf(location.getLongitude()));
        }
        else {
            locatioInfoTextView.setText("Unable to locate you :( ");
        }
    }

    @Override
    public void onSuccess() {
        //succesfully connected
        Toast toast = Toast.makeText(this, "Successfully connected to google play services", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onFailure() {
        //unable to connect
        Toast toast = Toast.makeText(this, "Failed to connect to google play services", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onPermissionError() {
        locationManager.requestPermission(this, LOCATION_ACCESS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_ACCESS_REQUEST_CODE:{

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    Toast toast = Toast.makeText(this, "Granted to access the location, you try locating me :)", Toast.LENGTH_SHORT);
                    toast.show();

                } else {

                    // permission denied
                    Toast toast = Toast.makeText(this, "FAIL!!! :(", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return;
            }
        }
    }
}
