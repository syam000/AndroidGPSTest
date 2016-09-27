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

### Implement listeners


