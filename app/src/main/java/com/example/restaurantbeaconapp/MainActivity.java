package com.example.restaurantbeaconapp;

import androidx.annotation.RequiresApi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

public class MainActivity extends Activity implements MonitorNotifier {

    private ImageView logo;
    private ImageView buttonMenu;
    private ImageView buttonBooking;

    private Animation logoAnimation;
    private TextView searching;

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;
    protected static final String TAG = "MainActivity";

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BeaconManager.getInstanceForApplication(this).addMonitorNotifier(this);

        verifyBluetooth();
        requestPermissions();

       buttonMenu = (ImageView) findViewById(R.id.menu);
       buttonBooking = (ImageView) findViewById(R.id.booking);
       logo = (ImageView) findViewById(R.id.logo);
       searching = findViewById(R.id.searchText);



        if (!ReferenceApplication.inRegion) {
          buttonMenu.setVisibility(View.GONE);
          buttonBooking.setVisibility(View.GONE);
          logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
          logo.startAnimation(logoAnimation);
        }

      buttonMenu.setOnClickListener(view -> {
          view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_anim));
            Intent intent = new Intent(view.getContext(), Menu.class);
            view.getContext().startActivity(intent);});



        buttonBooking.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_anim));
            Intent intent = new Intent(view.getContext(), Booking.class);
            view.getContext().startActivity(intent);});



    }

    @Override
    public void didEnterRegion(Region region) {
        logo.clearAnimation();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //textView.setVisibility(View.VISIBLE);
                buttonMenu.setVisibility(View.VISIBLE);
               buttonBooking.setVisibility(View.VISIBLE);
                logo.setVisibility(View.GONE);
                searching.setVisibility(View.GONE);
            }
        });
/*
        buttonMenu.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Menu.class);
            view.getContext().startActivity(intent);});

        buttonBooking.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Booking.class);
            view.getContext().startActivity(intent);});

 */
    }

    @Override
    public void didExitRegion(Region region) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                logo.setVisibility(View.VISIBLE);
                searching.setVisibility(View.VISIBLE);
                buttonMenu.setVisibility(View.GONE);
                buttonBooking.setVisibility(View.GONE);
                logoAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.logo_anim);
                logo.startAnimation(logoAnimation);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void didDetermineStateForRegion(int state, Region region) {
    }




    private void verifyBluetooth() {
        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finishAffinity();
                    }
                });
                builder.show();
            }
        }
        catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    finishAffinity();
                }

            });
            builder.show();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (this.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (!this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("This app needs background location access");
                            builder.setMessage("Please grant location access so this app can detect beacons in the background.");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                                @TargetApi(23)
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                            PERMISSION_REQUEST_BACKGROUND_LOCATION);
                                }

                            });
                            builder.show();
                        }
                        else {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Functionality limited");
                            builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons in the background.  Please go to Settings -> Applications -> Permissions and grant background location access to this app.");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                }

                            });
                            builder.show();
                        }
                    }
                }
            } else {
                if (!this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                            PERMISSION_REQUEST_FINE_LOCATION);
                }
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.  Please go to Settings -> Applications -> Permissions and grant location access to this app.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "fine location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
            case PERMISSION_REQUEST_BACKGROUND_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "background location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

}