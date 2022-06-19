package com.opylypiv.myhosp;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ExpandableListView listView;
    String line = "";
    String user_name;
    String currentUID;

    String deepLink = null;

    FirebaseFirestore db;

    ArrayList<Hospital> hospitallist;
    ListView hospitallistview;
    Double currentlatitude;
    Double currentlongtitude;

    private LocationRequest locationRequest;
    FirebaseUser user;
    static boolean isDoctor;

    String idhosp;


    public static NavController navController;
    private AppBarConfiguration appBarConfiguration;
    Toolbar toolbar;


    Intent intent;
    private Object FragmentHospitalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout dwl = (DrawerLayout) findViewById(R.id.drawer_layout);


        if (user != null) {
            String name = user.getDisplayName();
            Log.d("nameuser", name);
        } else {
            Log.d("nameuser", "null");

        }

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        new AppBarConfiguration.Builder(navController.getGraph()).build();
        setSupportActionBar(toolbar);
        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();

        NavigationUI.setupActionBarWithNavController(this, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {

            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setOpenableLayout(dwl)
                        .build();


    }

    public void getInvitation() {

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink().getLastPathSegment() + "";
                            idhosp = deepLink.replace("hosp", "");
                            Log.i("hospital_id", idhosp + "");

                        } else {
                            Bundle extras = getIntent().getExtras();
                            if (extras != null) {
                                idhosp = extras.getString("idhosp");
                                Log.i("hospital_id", idhosp + "");

                            }
                        }

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "getDynamicLink:onFailure", e);
                    }
                });
        for (int i = 150; i > 0; --i) {
            if (DoctorList.alldoctors.get(i).getDoctorUID().equals(currentUID)) {
                user_name = DoctorList.alldoctors.get(i).getFullname();
                isDoctor = true;
                Log.d("username", user_name);
            }
        }
        return;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Операции для выбранного пункта меню
        switch (item.getItemId()) {
            case R.id.profile:

            case R.id.massages:
                return true;
            case R.id.singoutitem:
                item.setTitle("ВЫЙТИ");
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.profile);
        if (user != null) {
            user.getDisplayName();
            item.setTitle(user_name);
            invalidateOptionsMenu();
        }
        SearchView searchView = (SearchView) item.getActionView();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                getLocation();
            }
        }
    }

    public void getLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {
                    LocationServices.getFusedLocationProviderClient(MainActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                            .removeLocationUpdates(this);
                                    Log.d("Latitude", currentlatitude + "");
                                    Log.d("longtitude", currentlongtitude + "");

                                    if ((locationResult != null && locationResult.getLocations().size() > 0)) {

                                        int index = locationResult.getLocations().size() - 1;
                                        currentlatitude = Double.valueOf(locationResult.getLocations().get(index).getLatitude() + "");
                                        currentlongtitude = Double.valueOf(locationResult.getLocations().get(index).getLongitude() + "");
                                        Log.d("Latitude", currentlatitude + "");
                                        Log.d("longtitude", currentlongtitude + "");


                                        Bundle bundle = new Bundle();
                                        bundle.putString("currentlatitude", currentlatitude + "");
                                        bundle.putString("currentlongtitude", currentlongtitude + "");
                                        navController.navigate(R.id.fragmentHospitalList, bundle);

                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();

                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
        }
    }

    private void turnOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(MainActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }


}