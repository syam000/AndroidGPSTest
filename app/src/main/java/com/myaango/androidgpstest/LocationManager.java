package com.myaango.androidgpstest;
//This class is main entry point class to listen for
//location changes

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by syamooo on 27/9/16.
 */
public class LocationManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    //location listener interface
    public interface LocationListener {
        void onSuccess();
        void onFailure();
        void onPermissionError();
    }

    //app context variable
    private Context context;
    //google api client variable
    private GoogleApiClient googleApiClient;
    //location listener variable
    private LocationListener locationListener;
    //google api connection status
    private boolean connectionStatus;
    //location access permission status
    private boolean locationAccessPermissionStatus;
    private int locationAccessPermissionreqResult = 0;

    //location manager constructor
    public LocationManager(Context context) {
        this.context = context;
    }

    //getter for appl context
    public Context getContext() {
        return context;
    }

    //setter for app context
    public void setContext(Context context) {
        this.context = context;
    }

    //getter for google api client variable
    public GoogleApiClient getGoogleApiClient() {

        try {
            if (googleApiClient == null) {
                //google api client not initialized yet, so initializing it
                googleApiClient = new GoogleApiClient.Builder(context)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
            }
            return googleApiClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //setter for google api client variable
    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    //getter for location listener variable
    public LocationListener getLocationListener() {
        return locationListener;
    }

    //setter for location listener variable
    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }

    //getter for connection status
    public boolean isConnectionStatus() {
        return connectionStatus;
    }

    //setter for connection status
    public void setConnectionStatus(boolean connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    //connect to google play services
    public void connect() {
        try {
            getGoogleApiClient().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //disconnect from google play services
    public void disconnect() {
        try {
            getGoogleApiClient().disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Location getLastknownLocation() {

        try {
            if (isConnectionStatus()) {
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(context, "No permission to access current location, please grant the permission first and try again!", Toast.LENGTH_SHORT);
                    toast.show();
                    locationAccessPermissionStatus = false;
                    if (locationListener != null){
                        locationListener.onPermissionError();
                    }
                    return null;
                }
                else {
                    locationAccessPermissionStatus = true;
                }
                return LocationServices.FusedLocationApi.getLastLocation(getGoogleApiClient());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public void requestPermission(){
        requestPermission((Activity) context, locationAccessPermissionreqResult);
    }

    public void requestPermission(Activity activity, int result){
        if (locationAccessPermissionStatus == false) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, result);
        }
    }

    //once get connected to google play service this callback will get fired
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setConnectionStatus(true);
        if (locationListener != null){
            locationListener.onSuccess();
        }
    }

    //this callback method will be fired when connection get suspended
    @Override
    public void onConnectionSuspended(int i) {
        setConnectionStatus(false);
        if (locationListener != null){
            locationListener.onFailure();
        }
    }

    //this callback will be fired when connection fails
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        setConnectionStatus(false);
        if (locationListener != null){
            locationListener.onFailure();
        }
    }

}
