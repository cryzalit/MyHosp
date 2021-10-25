package com.opylypiv.myhosp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorProfile extends AppCompatActivity {
    String currentiddoctor;
    String currentidhosp;
    String currentfullname;
    String currentspec;
    String currentpoint;
    String currentuser;
    String currentphoto;

    EditText textcomment;
    RatingBar setpoint;

    Doctor currentdoc;
    FirebaseFirestore db_sendcoment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db_sendcoment = FirebaseFirestore.getInstance();


        setContentView(R.layout.activity_doctor_profile);
        TextView name = (TextView) findViewById(R.id.name_doctor_profile);
        TextView profession = (TextView) findViewById(R.id.profession_doctor_profile);
        RatingBar point = (RatingBar) findViewById(R.id.ratingbar_doctror_profile);
        ImageView photo = (ImageView) findViewById(R.id.photo_doctor_profile);
        Button send_comment = (Button) findViewById(R.id.send);
        textcomment = (EditText) findViewById(R.id.textcomment);
        setpoint = (RatingBar) findViewById(R.id.setRating);

        Intent intent = getIntent();
        currentiddoctor = intent.getStringExtra("id");
        currentidhosp = intent.getStringExtra("idhosp");
        currentfullname = intent.getStringExtra("name");
        currentspec = intent.getStringExtra("spec");
        currentpoint = intent.getStringExtra("point");
        currentphoto = intent.getStringExtra("photo");

        name.setText(currentfullname);
        profession.setText(currentspec);
        point.setRating(Float.parseFloat(currentpoint.trim()));


    }

    public void sendcomment(View v) {
        Comment comment = new Comment();
        comment.setText(textcomment.getText().toString().trim());
        comment.setPoint(setpoint.getRating() + "");
        comment.setIdhosp(currentidhosp);
        comment.setIddoctor(currentiddoctor);
        comment.setIduser(currentuser);

        db_sendcoment.collection("comments").document(currentiddoctor + " " + currentidhosp)
                .set(comment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Succes", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Error", "Error writing document", e);
                    }
                });
    }


}