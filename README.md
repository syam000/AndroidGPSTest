# AndroidGPSTest
An android application to show how to get the gps location of the device using google play services

## Requirements

### Setup google play services dependencies

Add the following to your gradle file (Module : app)

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:24.2.1'
    compile 'com.google.android.gms:play-services:9.6.1'
}
```

### Setup android app permissions

```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.myaango.androidgpstest">
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
</manifest>

```

### Get connected to Google play services

```

//create and instance of google api client
googleApiClient = new GoogleApiClient.Builder(context)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();

//connect to the services
googleApiClient.connect();

```

### Implement listeners

```
//this callback will be fired when the client get connected 
@Override
public void onConnected(@Nullable Bundle bundle) {
        
}

//this callback method will be fired when connection get suspended
@Override
public void onConnectionSuspended(int i) {
        
}

//this callback will be fired when connection fails
@Override
public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
       
}

```

Once connected you will be able to call the last known location. 

### Call to get last known location

```

public Location getLastknownLocation() {
	return LocationServices.FusedLocationApi
							   .getLastLocation(googleApiClient);
}

```

