package com.opylypiv.myhosp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DoctorProfile extends AppCompatActivity {
    int currentiddoctor;
    String currentidhosp;
    String currentfullname;
    String currentspec;
    String currentpoint;
    int currentuser;
    String currentphoto;

    EditText textcomment;
    RatingBar setpoint;

    Doctor currentdoc;
    FirebaseFirestore db_sendcoment;
    ListView listcomments;
    ArrayList<Comment> comments;
    Comment c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db_sendcoment = FirebaseFirestore.getInstance();


        setContentView(R.layout.activity_doctor_profile);
        TextView name = findViewById(R.id.name_doctor_profile);
        TextView profession = findViewById(R.id.profession_doctor_profile);
        RatingBar point = findViewById(R.id.ratingbar_doctror_profile);
        ImageView photo = findViewById(R.id.photo_doctor_profile);
        Button send_comment = findViewById(R.id.send);
        textcomment = findViewById(R.id.comment);
        setpoint = findViewById(R.id.setRating);
        listcomments = findViewById(R.id.listcomments);
        comments = new ArrayList<Comment>();

        Intent intent = getIntent();
        currentiddoctor = Integer.parseInt(intent.getStringExtra("id"));
        currentidhosp = intent.getStringExtra("idhosp");

        Log.d("currentiddoctor", currentiddoctor + "");
        Log.d("currentidhosp", currentidhosp + "");


        getComments();

        for (int i = 150; i > 0; --i) {
            if (DoctorList.alldoctors.get(i).getId() == currentiddoctor) {
                Log.d("Error", currentiddoctor + "");
                currentdoc = DoctorList.alldoctors.get(i);
                name.setText(DoctorList.alldoctors.get(i).getFullname() + "");
                profession.setText(DoctorList.alldoctors.get(i).getSpec() + "");
                point.setRating(Float.parseFloat(DoctorList.alldoctors.get(i).getPoint() + ""));
                point.setStepSize(0.1f);

                return;
            }
        }
    }

    public void getComments() {
        c = new Comment();
        db_sendcoment.collection("comments").whereEqualTo("iddoctor", currentiddoctor)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                c = document.toObject(Comment.class);
                                Log.d("SCS", "DocumentSnapshot data: " + document.getData());
                                comments.add(c);

                            }
                            CommentsListAdapter cma = new CommentsListAdapter(DoctorProfile.this, comments);
                            listcomments.setAdapter(cma);

                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }

                    }


                });

    }


    public void sendcomment(View v) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Comment comment = new Comment();
        comment.setId(timestamp.getTime());
        comment.setText(textcomment.getText().toString().trim());
        comment.setPoint(setpoint.getRating() + "");
        comment.setIdhosp(currentdoc.getIdhosp());
        comment.setIddoctor(currentiddoctor);
        comment.setIduser(currentuser);
        comment.setTextanswer(null);

        db_sendcoment.collection("comments").document(timestamp.getTime() + "")
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
                        Log.w("ErrorSENDCOMMENT", "Error writing document", e);
                    }
                });
        setSetpoint();
        onBackPressed();


    }

    public void setSetpoint() {

        DocumentReference docRef = db_sendcoment.collection("hosp_" + currentidhosp).document(currentiddoctor + "");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        currentdoc = document.toObject(Doctor.class);
                        Log.d("VSEOK", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("ERROR", "No such document");
                    }
                } else {
                    Log.d("ERROR", "get failed with ", task.getException());
                }
            }
        });

        FirebaseFirestore answer = FirebaseFirestore.getInstance();
        Map<String, Object> newpoint = new HashMap<>();
        newpoint.put("sumpoints", currentdoc.getSumpoints() + setpoint.getRating());
        newpoint.put("points", currentdoc.getPoints() + 1);
        answer.collection("hosp_" + currentidhosp).document(currentiddoctor + "").update(newpoint).addOnSuccessListener(new OnSuccessListener<Void>() {
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


    public void goLogin(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }


}