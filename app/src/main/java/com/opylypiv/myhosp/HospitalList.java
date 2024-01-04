package com.opylypiv.myhosp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HospitalList extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    ArrayList<Hospital> hospitallist;
    ListView hospitallistview;
    Double currentlatitude;
    Double currentlongtitude;

    private LocationRequest locationRequest;


    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                // Handle permission denied
            }
        }
    }

    private void getLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                currentlatitude = location.getLatitude();
                currentlongtitude = location.getLongitude();
                // Use latitude and longitude
            }
        });
    }
    public void getNearHospitals() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("hospital_data")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("succeschechfirebase", task.isSuccessful() + "");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Latitude", currentlatitude + "");
                                Log.d("longtitude", currentlongtitude + "");

                                Hospital hospital = document.toObject(Hospital.class);
                                hospitallist.add(hospital);
                                HospitalListAdapter hla = new HospitalListAdapter(HospitalList.this, currentlatitude, currentlatitude, hospitallist);
                                hospitallistview.setAdapter(hla);

                            }
                        }
                    }
                });
    }

}