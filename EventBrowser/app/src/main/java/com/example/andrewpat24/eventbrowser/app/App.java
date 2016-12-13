package com.example.andrewpat24.eventbrowser.app;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.UUID;

/**
 * Created by Ajinkya on 10/12/16.
 */

public class App extends Application implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static App application;
    private static UUID sStoryListActivity;
    private GoogleApiClient mGoogleApiClient;
    private static Location sLastLocation;
    private String mLatitudeText;
    private String mLongitudeText;

    @Override
    public void onCreate() {
        application = this;

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mGoogleApiClient.connect();
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        mGoogleApiClient.disconnect();
        super.onTerminate();
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

    public static UUID getStoryListActivityUUID() {
        return sStoryListActivity;
    }

    public static void setStoryListActivityUUID(UUID current) {
        sStoryListActivity = current;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
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
        sLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        Log.i("Location ", String.valueOf(sLastLocation.getLatitude()) + " " + String.valueOf(sLastLocation.getLongitude()));
    }

    public static Location getLastLocation(){
        return sLastLocation;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
