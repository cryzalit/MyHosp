package com.opylypiv.myhosp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorProfile extends AppCompatActivity {
    String currentid;
    String currentfullname;
    String currentspec;
    String currentpoint;
    String currentphoto;

    Doctor currentdoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_doctor_profile);
        TextView name = (TextView)findViewById(R.id.name_doctor_profile);
        TextView profession = (TextView)findViewById(R.id.profession_doctor_profile);
        RatingBar point = (RatingBar)findViewById(R.id.ratingbar_doctror_profile);
        ImageView photo = (ImageView) findViewById(R.id.photo_doctor_profile);

        Intent intent = getIntent();
        currentid = intent.getStringExtra("id");
        currentfullname = intent.getStringExtra("name");
        currentspec = intent.getStringExtra("spec");
        currentpoint = intent.getStringExtra("point");
        currentphoto = intent.getStringExtra("photo");
        name.setText(currentfullname);
        profession.setText(currentspec);
        point.setRating(Float.parseFloat(currentpoint.trim() + ".0"));


    }





}