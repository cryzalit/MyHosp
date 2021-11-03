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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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
    FirebaseFirestore db_getcomments;
    ListView listcomments;

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

        Intent intent = getIntent();
        currentiddoctor = Integer.parseInt(intent.getStringExtra("id"));
        getComments();

        for (int i = 150; i > 0; --i) {
            if (DoctorList.alldoctors.get(i).getId() == currentiddoctor) {
                Log.d("Error", currentiddoctor + "");
                currentdoc = DoctorList.alldoctors.get(i);
                name.setText(DoctorList.alldoctors.get(i).getFullname() + "");
                profession.setText(DoctorList.alldoctors.get(i).getSpec() + "");
                point.setRating(Float.parseFloat(DoctorList.alldoctors.get(i).getPoint() + ""));
                return;
            }


        }

    }

    public void getComments() {
        db_sendcoment.collection("comments").whereEqualTo("iddoctor", currentiddoctor)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Comment c = document.toObject(Comment.class);
                                ArrayList<Comment> comments = new ArrayList<>();
                                comments.add(c);
                                CommentsListAdapter cma = new CommentsListAdapter(DoctorProfile.this, comments);
                                listcomments.setAdapter(cma);
                                Log.d("Error", comments.get(0).iddoctor + "");

                            }
                        } else {
                            Log.d("Error", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    public void sendcomment(View v) {
        Comment comment = new Comment();
        comment.setText(textcomment.getText().toString().trim());
        comment.setPoint(setpoint.getRating() + "");
        comment.setIdhosp(currentdoc.getIdhosp());
        comment.setIddoctor(currentdoc.getId());
        comment.setIduser(currentuser);
        comment.setAnswer(null);

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
        onBackPressed();
    }


}